package scraper.object;

public class MyClassLoader extends ClassLoader {

	  public Class loadClass (String name) {
		return null;
	   // if (the 'name' class does not need to be transformed) {
	   //   return super.loadClass(name);
	    //}
		  
		  
	    // find the .class file
	    // construct a ClassReader to parse this .class file
	    // construct a ClassWriter1 to create the transformed class
	    // construct a ClassWriter2 to create the corresponding interface
	    // construct a GetterSetterClassAdapter to modify the original class
	    // call classReader.accept(classAdapter)

	    // call defineClass(classWriter2.toByteArray) to create the interface
	    // return define(classWriter1.toByteArray) to create the modified class
	  }
	}