package de.tr7zw.nbtinjector;

import java.io.IOException;
import java.lang.reflect.Constructor;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

/**
 * This class uses Javassist to create wrapped Entity/Tile classes during the
 * runtime
 * 
 * @author tr7zw
 *
 */
public class ClassGenerator {

	/**
	 * Hidden Constructor
	 */
	private ClassGenerator() {

	}

	private static final String GENERATOR_PACKAGE = "de.tr7zw.nbtinjector.generated";

	/**
	 * Wrappes a given class with the INBTWrapper interface
	 * 
	 * @param classPool     The Javassist classpool
	 * @param originalClass The NMS Tile/Entity class
	 * @param writeMethod   String of the saving method
	 * @param readMethod    String of the loading method
	 * @param extraDataKey  Key used to save the custom nbt
	 * @return Class reference to the generated class
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws IOException
	 */
	protected static Class<?> wrapNbtClass(ClassPool classPool, Class<?> originalClass, String writeMethod,
			String readMethod, String extraDataKey) throws NotFoundException, CannotCompileException, IOException {
		classPool.insertClassPath(new LoaderClassPath(ClassGenerator.class.getClassLoader()));

		CtClass generated = classPool.makeClass("de.tr7zw.nbtinjector.generated." + originalClass.getSimpleName());

		CtClass wrapperInterface = classPool.get(INBTWrapper.class.getName());
		generated.setInterfaces(new CtClass[] { wrapperInterface });
		generated.setSuperclass(classPool.get(originalClass.getName()));

		classPool.importPackage("net.minecraft.server." + MinecraftVersion.getVersion().name().replace("MC", "v"));
		classPool.importPackage(NBTCompound.class.getPackage().getName());
		classPool.importPackage(GENERATOR_PACKAGE);

		generated.addField(CtField.make("public NBTCompound $extraCompound = new NBTContainer();", generated));
		generated.addMethod(CtMethod
				.make("public NBTCompound getNbtData() {\n" + "  return this.$extraCompound;\n" + "}", generated));
		generated.addMethod(CtMethod.make("public NBTTagCompound readExtraCompound(NBTTagCompound root) {\n"
				+ "  NBTTagCompound compound = root.getCompound(\"" + extraDataKey + "\");\n"
				+ "  this.$extraCompound = new NBTContainer(compound);\n"
				// + " System.out.println(\"Read: \" + root);\n"
				+ "  return root;" + "}", generated));
		generated.addMethod(CtMethod.make("public NBTTagCompound writeExtraCompound(NBTTagCompound root) {\n"
				+ "  NBTBase compound = (NBTBase) this.$extraCompound.getCompound();\n"
				+ "  NBTTagCompound newRoot = new NBTTagCompound();\n" + "  newRoot.set(\"" + extraDataKey
				+ "\", compound);\n" + "  root.a(newRoot);\n" + "  return root;"// Merge
				+ "}", generated));

		generated.addMethod(CtMethod.make(writeMethod, generated));
		generated.addMethod(CtMethod.make(readMethod, generated));

		// Overwrite constructors
		for (Constructor<?> constructor : originalClass.getConstructors()) {
			String paramString = "";
			String paramNameString = "";
			int c = 0;
			for (Class<?> clazz : constructor.getParameterTypes()) {
				if (c != 0) {
					paramString += ",";
					paramNameString += ",";
				}
				paramString += clazz.getName() + " param" + c;
				paramNameString += "param" + c;
				c++;
			}
			generated.addConstructor(CtNewConstructor.make("public " + originalClass.getSimpleName() + "(" + paramString
					+ ") {\n" + "  super(" + paramNameString + ");\n" + "}", generated));
		}

		generated.writeFile("nbtinjector_generated");
		return generated.toClass(INBTWrapper.class.getClassLoader(), INBTWrapper.class.getProtectionDomain());
	}

	/**
	 * Creates a class for the EntityTypes getter for a given entity
	 * 
	 * @param classPool   The Javassist classpool
	 * @param targetClass The NMS Entity class
	 * @return Class reference to the generated class
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws IOException
	 */
	protected static Class<?> createEntityTypeWrapper(ClassPool classPool, Class<?> targetClass)
			throws NotFoundException, CannotCompileException, IOException {
		classPool.insertClassPath(new LoaderClassPath(ClassGenerator.class.getClassLoader()));

		CtClass generated = classPool.makeClass(GENERATOR_PACKAGE + ".entityCreator." + targetClass.getSimpleName());

		CtClass wrapperInterface = classPool.get(ClassWrapper.NMS_ENTITYTYPES.getClazz().getName() + "$b");
		generated.setInterfaces(new CtClass[] { wrapperInterface });

		classPool.importPackage("net.minecraft.server." + MinecraftVersion.getVersion().name().replace("MC", "v"));
		classPool.importPackage(GENERATOR_PACKAGE);

		generated.addMethod(CtMethod.make("public Entity create(EntityTypes var1, World var2) {\n" + "  return new "
				+ targetClass.getName() + "(var1, var2);\n" + "}", generated));

		generated.writeFile("nbtinjector_generated");
		return generated.toClass(INBTWrapper.class.getClassLoader(), INBTWrapper.class.getProtectionDomain());
	}

	/**
	 * Wrappes a given Entity class with the INBTWrapper interface
	 * 
	 * @param classPool     The Javassist classpool
	 * @param originalClass The NMS Entity class
	 * @param extraDataKey
	 * @return Class reference to the generated class
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws IOException
	 */
	protected static Class<?> wrapEntity(ClassPool classPool, Class<?> originalClass, String extraDataKey)
			throws NotFoundException, CannotCompileException, IOException {
		String writeReturn = MinecraftVersion.getVersion().getVersionId() > MinecraftVersion.MC1_10_R1.getVersionId()
				? "NBTTagCompound"
				: "void";
		String writeName = ReflectionMethod.NMS_ENTITY_GET_NBT.getMethodName();
		String readName = ReflectionMethod.NMS_ENTITY_SET_NBT.getMethodName();
		if (MinecraftVersion.getVersion().getVersionId() < MinecraftVersion.MC1_11_R1.getVersionId()) {
			writeName = "b";
			readName = "f";
		}

		String writeMethod = "public " + writeReturn + " " + writeName + "(NBTTagCompound compound) {\n" + "  super."
				+ writeName + "(compound);\n" + "  compound = writeExtraCompound(compound);\n"
				// + " System.out.println(\"Saved-Data: \" + compound);"
				+ "  " + (!"void".equals(writeReturn) ? "return compound;" : "") + "}";
		String readMethod = "public void " + readName + "(NBTTagCompound compound) {\n" + "  super." + readName
				+ "(compound);\n" + "  readExtraCompound(compound);\n" + "}";
		return wrapNbtClass(classPool, originalClass, writeMethod, readMethod, extraDataKey);
	}

	/**
	 * Wrappes a given Tile class with the INBTWrapper interface
	 * 
	 * @param classPool     The Javassist classpool
	 * @param originalClass The NMS Tile class
	 * @param extraDataKey
	 * @return Class reference to the generated class
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws IOException
	 */
	protected static Class<?> wrapTileEntity(ClassPool classPool, Class<?> originalClass, String extraDataKey)
			throws NotFoundException, CannotCompileException, IOException {
		String writeReturn = MinecraftVersion.getVersion().getVersionId() > MinecraftVersion.MC1_9_R1.getVersionId()
				? "NBTTagCompound"
				: "void";
		String writeName = ReflectionMethod.TILEENTITY_GET_NBT.getMethodName();
		String readName = ReflectionMethod.TILEENTITY_SET_NBT.getMethodName();
		String writeMethod = "public " + writeReturn + " " + writeName + "(NBTTagCompound compound) {\n"
				+ "  compound = writeExtraCompound(compound);\n"
				// + " System.out.println(\"Save: \" +compound);\n"
				+ "  " + (!"void".equals(writeReturn) ? "return " : "") + "super." + writeName + "(compound);\n" + "}";
		String readMethod = "public void " + readName + "(NBTTagCompound compound) {\n" + "  super." + readName
				+ "(compound);\n" + "  readExtraCompound(compound);\n"
				// + " System.out.println(\"Read: \" +compound);\n"
				+ "}";
		return wrapNbtClass(classPool, originalClass, writeMethod, readMethod, extraDataKey);
	}

}
