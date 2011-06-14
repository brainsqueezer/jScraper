package scraper.object;

import java.lang.reflect.Field;

public class ClassValidator {

//Simple simp = new simple();

	public ClassValidator(String classname) {
		try {
			Class c  = Class.forName(classname);
			Field[] fields =c.getClass().getDeclaredFields();
//			 For( int I = 0 ; I < fields.length; I++ ){
//			if (false == determineIfSetter(fields[i]) ){
//		               // I would like to add the name and arguments to the
//			interface here
//		               inter.addSetter("set"+ fields[i].getName );
//		       }
		       // do the same with getter
//		 }
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//> 
//>
// I would like to create a new interface specification here
// Interface inter = new Interface(); // ---> ?
//

	}
}