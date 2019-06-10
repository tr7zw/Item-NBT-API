package de.tr7zw.nbtinjector;

import javassist.*;
import java.io.IOException;
import java.lang.reflect.Constructor;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;

public class ClassGenerator {

	public static Class<?> wrapNbtClass(ClassPool classPool, Class<?> originalClass, String writeMethod, String readMethod, String extraDataKey) throws ReflectiveOperationException, NotFoundException, CannotCompileException {
		classPool.insertClassPath(new LoaderClassPath(ClassGenerator.class.getClassLoader()));

		CtClass generated = classPool.makeClass("de.tr7zw.nbtinjector.generated." + originalClass.getSimpleName());

		CtClass wrapperInterface = classPool.get(INBTWrapper.class.getName());
		generated.setInterfaces(new CtClass[] { wrapperInterface });
		generated.setSuperclass(classPool.get(originalClass.getName()));
		

		classPool.importPackage("net.minecraft.server." + MinecraftVersion.getVersion().name().replace("MC", "v"));
		classPool.importPackage(NBTCompound.class.getPackage().getName());
		classPool.importPackage("de.tr7zw.nbtinjector.generated");

		generated.addField(CtField.make("public NBTCompound $extraCompound = new NBTContainer();", generated));
		generated.addMethod(CtMethod.make("public NBTCompound getNbtData() {\n"
				+ "  return this.$extraCompound;\n"
				+ "}", generated));
		generated.addMethod(CtMethod.make("public NBTTagCompound readExtraCompound(NBTTagCompound root) {\n"
				+ "  NBTTagCompound compound = root.getCompound(\"" + extraDataKey + "\");\n"
				+ "  this.$extraCompound = new NBTContainer(compound);\n"
				//+ "  System.out.println(\"Read: \" + root);\n"
				+ "  return root;"
				+ "}", generated));
		generated.addMethod(CtMethod.make("public NBTTagCompound writeExtraCompound(NBTTagCompound root) {\n"
				+ "  NBTBase compound = (NBTBase) this.$extraCompound.getCompound();\n"
				+ "  NBTTagCompound newRoot = new NBTTagCompound();\n"
				+ "  newRoot.set(\"" + extraDataKey + "\", compound);\n"
				+ "  root.a(newRoot);\n"
				+ "  return root;"// Merge
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
			generated.addConstructor(CtNewConstructor.make("public " + originalClass.getSimpleName() + "(" + paramString + ") {\n"
					+ "  super(" + paramNameString + ");\n"
					+ "}", generated));
		}

		try {
			generated.writeFile("nbtinjector_generated");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return generated.toClass(INBTWrapper.class.getClassLoader(), INBTWrapper.class.getProtectionDomain());
	}

	public static Class<?> wrapEntity(ClassPool classPool, Class<?> originalClass, String extraDataKey) throws ReflectiveOperationException, NotFoundException, CannotCompileException {
		String writeReturn = MinecraftVersion.getVersion().getVersionId() > MinecraftVersion.MC1_10_R1.getVersionId() ? "NBTTagCompound" : "void";
		String writeMethod = "public " + writeReturn + " b(NBTTagCompound compound) {\n"
				+ "  super.b(compound);\n"
				+ "  compound = writeExtraCompound(compound);\n"
				//+ "  System.out.println(\"Saved-Data: \" + compound);"
				+ "  " + (!"void".equals(writeReturn) ? "return compound;" : "")
				+ "}";
		String readMethod = "public void f(NBTTagCompound compound) {\n"
				+ "  super.f(compound);\n"
				+ "  readExtraCompound(compound);\n"
				+ "}";
		return wrapNbtClass(classPool, originalClass, writeMethod, readMethod, extraDataKey);
	}

	public static Class<?> wrapTileEntity(ClassPool classPool, Class<?> originalClass, String extraDataKey) throws ReflectiveOperationException, NotFoundException, CannotCompileException {
		String writeReturn = MinecraftVersion.getVersion().getVersionId() > MinecraftVersion.MC1_9_R1.getVersionId() ? "NBTTagCompound" : "void";
		String writeName = MinecraftVersion.getVersion().getVersionId() > MinecraftVersion.MC1_9_R1.getVersionId() ? "save" : "b";
		String writeMethod = "public " + writeReturn + " " + writeName + "(NBTTagCompound compound) {\n"
				+ "  compound = writeExtraCompound(compound);\n"
				//+ "  System.out.println(\"Save: \" +compound);\n" 
				+ "  " + (!"void".equals(writeReturn) ? "return " : "") + "super." + writeName + "(compound);\n"
				+ "}";
		String readMethod = "public void a(NBTTagCompound compound) {\n"
				+ "  super.a(compound);\n"
				+ "  readExtraCompound(compound);\n"
				//+ "  System.out.println(\"Read: \" +compound);\n" 
				+ "}";
		return wrapNbtClass(classPool, originalClass, writeMethod, readMethod, extraDataKey);
	}

}
