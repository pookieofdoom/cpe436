package adinh03.calpoly.edu.todorecyclerview;

import com.firebase.client.Firebase;

/**
 * Created by pooki on 10/20/2016.
 */

public class ToDoRecyclerView extends android.app.Application{

   @Override
   public void onCreate() {
      super.onCreate();
      Firebase.setAndroidContext(this);
   }
}
