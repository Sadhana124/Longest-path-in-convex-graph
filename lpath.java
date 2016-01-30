
package bp;

public class lpath {
    String path;
    int len;

    public lpath( String path1, int len1){
        path = path1;
        len = len1;
    }
    public lpath(){
            len = 0;
            path = "";
    };
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
