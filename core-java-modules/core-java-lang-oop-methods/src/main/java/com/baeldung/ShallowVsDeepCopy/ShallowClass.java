
package shallowVsDeepCopy;
 
import java.util.ArrayList;

public class ShallowClass {
    ArrayList<Object> list;
     public ShallowClass() {
        list = new ArrayList<>();
    }
     public ShallowClass shallowCopy() {
        ShallowClass copy = new ShallowClass();
        copy.list = new ArrayList<>(this.list); 
        return copy;
    }
}

 