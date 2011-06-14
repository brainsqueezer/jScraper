
package scraper.object;

import java.util.List;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;

public class GetterSetterClassAdapter extends ClassAdapter {

  ClassVisitor icv; // to generate the interface
  List fields;
  List exisitingGetters;
  List existingSetters;

  public GetterSetterClassAdapter (ClassVisitor cv, ClassVisitor icv) {
    super(cv);
    this.icv = icv;
  }
  
  
  public void visit () {
    // if you want the transformed class to implement the generated
    // interface, modify the 'interfaces' argument here
  //}
  //void visitField (...) {
    // add the field to the 'fields' list
    super.visitField(0, null, null, exisitingGetters, null);
  }
  
  public CodeVisitor visitMethod () {
	return null;
    //if (isSetter()) {
      // add it to the 'existingSetters' list
    //} else if (isGetter()) {
      // add it to the 'existingGetters' list
    //}
    //return new GetterSetterCodeAdapter(super.visitMethod(...));
  }
  public void visitEnd () {
    // at this stage all fields and methods have been visited;
    // we can therefore test if some getter/setter are missing:
    // for each element in 'fields'
      // if 'existingGetters' do not contain this element
        // generate the getter method in cv
        // add the getter method to the interface with icv
      // if 'existingSetters' do not contain this element
        // generate the setter method
        // add the setter method to the interface with icv
    // super.visitEnd();
    // icv.visitEnd();
  }
}

