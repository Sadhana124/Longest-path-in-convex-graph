
package bp;

public class Maxpair {
   int i, j, k;
   String path;
   int len;

   public Maxpair(int p, int q, int r, String path1, int len1){
            path = path1;
            i = p; j = q; k = r;
            len = len1;
    }

   public Maxpair(){
            len = 0;
            path = "";
   }

   public int getLen(){
            return(len);
   }

   public String getPath(){
            return(path);
   }

   public void setLen(int len1){
            len = len1;
   }

   public void setPath(String path1){
            path = path1;
   }
}
