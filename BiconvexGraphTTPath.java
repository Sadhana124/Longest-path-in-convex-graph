
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.io.DataOutputStream;
//import java.io.FileOutputStream;
//import java.lang.reflect.Array;

import bp.Maxpair;
import bp.lpath;

public class BiconvexGraphTTPath {
	// public static String ah[] =new String[26];
	public static String ch[] = { "", "S1", "S2", "S3"};
	public static String ah[] = { "", "T1", "T2", "T3","T4"};
	public static String order[] = { "", "T1","T2","T3","S2","T4","S1","S3"};
	public static int left[] = { 0, 0, 0, 0, 1, 0, 2, 1};

	public static int[][][] len = new int[37][37][37];
	public static String[][][][] path = new String[37][37][37][37];
        public static int[][][] npaths = new int[37][37][37];
	public static BufferedWriter output;// = new BufferedWriter(new
	static// FileWriter(file));
	Maxpair mp = new Maxpair();

	public static void main(String args[]) throws IOException {
		File file = new File("BiconvexPathTTPath.txt");
		output = new BufferedWriter(new FileWriter(file));
		int index = 7;
		for (int j = 1; j <= index; j++) {
			output.write(j + "-------");
			output.newLine();
			for (int i = j; i >= 1; i--) {
				if ((i == j) && testStableVertex(order[i])) {
					len[i][i][i] = 1;
					path[i][i][i][0] = order[i];
                                        npaths[i][i][i] = 1;
					printLen(i, i, i, "");
					output.write("  ");
					printPath(i, i, i, 1, "");
					output.newLine();
				} else {
					for (int k = i; k < j; k++) {
						if (testStableVertex(order[k])) {
							len[k][i][j] = len[k][i][j - 1];
                                                        npaths[k][i][j] = npaths[k][i][j-1];
                                                        for(int np = 0; np < npaths[k][i][j]; np++)
                                                                path[k][i][j][np] = path[k][i][j - 1][np];
							printLen(k, i, j, "");
							output.write("  ");
							printPath(k, i, j, npaths[k][i][j], "");
							output.newLine();
						}
					}
					if (testStableVertex(order[j])) {
						len[j][i][j] = 1;
						path[j][i][j][0] = order[j];
                                                npaths[j][i][j] = 1;
						printLen(j, i, j, "");
						output.write("  ");
						printPath(j, i, j, 1, "");
						output.newLine();

					} else {
						if (i <= left[j]) {
                                                        output.newLine();
                                                        output.write("\nexecute process of H(" + i + ","
									+ j + ") is getting called");
							output.newLine();
							process(i, j);

						}
					}

				}
			}

		}
		ArrayList<Maxpair> mps = new ArrayList<Maxpair>();
		mps.add(mp);
                String mpspath;
                int found;
		for (int i = 1; i <= index; i++)
			for (int j = 1; j <= index; j++)
				for (int k = 1; k <= index; k++) {
					if (len[i][j][k] == mp.getLen()) {
                                            for( int np = 0; np < npaths[i][j][k]; np++){
                                                found = 0;
                                                for (int p = 0; p < mps.size(); p++){
                                                    mpspath = mps.get(p).getPath();
                                                    if(path[i][j][k][np]== mpspath){
                                                        found = 1;
                                                        break;
                                                    }
                                                }
                                                if(found == 0)
                                                {
                                                        Maxpair mp1 = new Maxpair(i, j, k, path[i][j][k][np],
                                                                        len[i][j][k]);
                                                        mps.add(mp1);
                                                }                                                
					}
                                    }
				}
		for (int i = 0; i < mps.size(); i++) {
                        output.newLine();
                        output.newLine();
                        if(i==0) output.write("Mps size " + mps.size());
                        output.newLine();
                        output.write("Testing ------------------\n");
			output.write("Longest path " + (i+1) + " " + mps.get(i).getPath()
					+ "  Length=" + mps.get(i).getLen() + " \n");
                        testMaximumPath(mps.get(i));
		}
		output.close();
	}

	private static void testMaximumPath(Maxpair mpi) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<String> neighT = new ArrayList<String>();
		ArrayList<String> neighT2 = new ArrayList<String>();
		String LastStableVertex = mpi.getPath().substring(
				mpi.getPath().lastIndexOf(",") + 1);
		String FirstStableVertex = mpi.getPath().substring(0,
				mpi.getPath().indexOf(","));
		// output.write("First stable vertex is "+FirstStableVertex+"\n");
		int LastStableOrderIndex = 0;
		int FirstStableOrderIndex = 0;
		boolean pathChanged = false;
		for (int i = 1; i < order.length; i++) {
			if (order[i].equalsIgnoreCase(LastStableVertex))
				LastStableOrderIndex = i;
			if (order[i].equalsIgnoreCase(FirstStableVertex))
				FirstStableOrderIndex = i;
		}
		// output.write("First stable vertex order is
		// "+FirstStableOrderIndex+"\n");
		for (int j = 1; j < ch.length; j++)
			for (int i = 1; i < order.length; i++) {
				if (order[i] == ch[j] && left[i] <= LastStableOrderIndex
						&& i > LastStableOrderIndex)
					neighT.add(ch[j]);
				if (order[i] == ch[j] && left[i] <= FirstStableOrderIndex
						&& i > FirstStableOrderIndex)
					neighT2.add(ch[j]);
			}

                lpath lp = new lpath();
                ArrayList<lpath> lpaths = new ArrayList<lpath>();
                String xPath;
                int flag = 0;
                int length;
                int nT = 0;
                int nT2 = 0;
                String s1 = "", s2 = "";
		for (int k = 0; k < neighT.size(); k++) {
                    if (mpi.getPath().indexOf(neighT.get(k)) == -1){
                        s1 = neighT.get(k);
                        nT++;
                    }
                }
                for (int k = 0; k < neighT2.size(); k++) {
                    if (mpi.getPath().indexOf(neighT2.get(k)) == -1){
                        s2 = neighT2.get(k);
                        nT2++;
                    }
                }
                if ( nT == nT2 && nT == 1 && s1.equalsIgnoreCase(s2))
                {
                        length = mpi.getLen() + 1;
                        xPath = ( mpi.getPath() + "," + s1 );
                        lp.setPath(xPath);
                        lp.setLen(length);
                        lpaths.add(lp);
                        output.write(" Actual length of the path is changed " + lp.getPath()
                                    + " Length=" + lp.getLen() +"\n");
                        xPath = ( s1 + "," + mpi.getPath());
                        lp.setPath(xPath);
                        lp.setLen(length);
                        lpaths.add(lp);
                        output.write(" Actual length of the path is changed " + lp.getPath()
                                    + " Length=" + lp.getLen() +"\n");
                }
                else{
                        if( nT > nT2){
                                for (int k = 0; k < neighT.size(); k++) {
                                    if (mpi.getPath().indexOf(neighT.get(k)) == -1) {
                                            pathChanged = true;
                                            length = mpi.getLen() + 1;
                                            xPath = ( mpi.getPath() + "," + neighT.get(k) );
                                            lp.setPath(xPath);
                                            lp.setLen(length);
                                            flag = 0;
                                           // lpath.add(xPath);
                                           // index++;
                                            for (int l = 0; l< neighT2.size(); l++){
                                                if (mpi.getPath().indexOf(neighT2.get(l)) == -1 && !neighT.get(k).equalsIgnoreCase(neighT2.get(l))){
                                                        String xPath2 = ( neighT2.get(l) + "," + xPath);
                                                        lp.setPath(xPath2);
                                                        lp.setLen(length + 1);
                                                        lpaths.add(lp);
                                                        flag = 1;
                                                        output.write(" Actual length of the path is changed " + lp.getPath()
                                                        + " Length=" + lp.getLen() +"\n");
                                                }
                                            }
                                            if( flag == 0){
                                                        lpaths.add(lp);
                                                        output.write(" Actual length of the path is changed " + lp.getPath()
                                                        + " Length=" + lp.getLen() +"\n");
                                            }
                                    }
                                }
                        }
                        else{
                                for (int k = 0; k < neighT2.size(); k++) {
                                    if (mpi.getPath().indexOf(neighT2.get(k)) == -1) {
                                            pathChanged = true;
                                            length = mpi.getLen() + 1;
                                            xPath = ( neighT2.get(k) + "," + mpi.getPath());
                                            lp.setPath(xPath);
                                            lp.setLen(length);
                                            flag = 0;
                                           // lpath.add(xPath);
                                           // index++;
                                            for (int l = 0; l< neighT.size(); l++){
                                                if (mpi.getPath().indexOf(neighT.get(l)) == -1 && !neighT2.get(k).equalsIgnoreCase(neighT.get(l))){
                                                        String xPath2 = ( xPath + "," + neighT.get(l) );
                                                        lp.setPath(xPath2);
                                                        lp.setLen(length + 1);
                                                        lpaths.add(lp);
                                                        flag = 1;
                                                        output.write(" Actual length of the path is changed " + lp.getPath()
                                                        + " Length=" + lp.getLen() +"\n");
                                                }
                                            }
                                            if( flag == 0){
                                                        lpaths.add(lp);
                                                        output.write(" Actual length of the path is changed " + lp.getPath()
                                                        + " Length=" + lp.getLen() +"\n");
                                            }
                                    }
                                }
                        }
                        if (!pathChanged){
                                output.write(" Actual length of the path is unchanged \n " );
                        }
                }
		return;
	}

	static void process(int i, int j) throws IOException {
		for (int y = left[j] + 1; y <= j - 1; y++) {
			for (int x = left[j]; x <= y - 1; x++) {
				if (testStableVertex(order[x]) && testStableVertex(order[y])) {
                                        output.write("x=" + x + " y=" + y + "\n");
                                        int w1 = len[x][i][j - 1];
                                      //  int np = npaths[x][i][j - 1];
                                        for(int np1 = 0; np1 < npaths[x][i][j - 1]; np1++){
                                            String p1 = path[x][i][j - 1][np1];
                                            output.newLine();
                                            printLen(x, i, j - 1, "w1= ");
                                            output.write("   ");
                                            //printPath(x, i, j - 1, npaths[x][i][j - 1], "P1= ");
                                            output.write("P1= Path(" + order[x] + ";" + i + "," + (j - 1) + ") =" + path[x][i][j - 1][np1]);
                                            output.newLine();

                                            for(int np2 = 0; np2 < npaths[y][x + 1][j - 1]; np2++){
                                                int w2 = len[y][x + 1][j - 1];
                                                String p2 = path[y][x + 1][j - 1][np2];
                                                printLen(y, x + 1, j - 1, "w2= ");
                                                output.write("   ");
                                                //printPath(y, x + 1, j - 1, "P2= ");
                                                output.write("P2= Path(" + order[y] + ";" + (x+1) + "," + (j - 1) + ") =" + path[y][x + 1][j - 1][np2]);

                                                if ((w1 + w2 + 1) > len[y][i][j]) {
                                                        output.write("\nUpdate path and length Uy=" + order[y] + " i=" + i + " j=" + j);
                                                        output.newLine();
                                                        len[y][i][j] = w1 + w2 + 1;
                                                        npaths[y][i][j] = 1;
                                                        path[y][i][j][0] = p1 + "," + order[j] + "," + p2;
                                                        setMaxPair(y, i, j);

                                                        printLen(y, i, j, "");
                                                        output.write("   ");
                                                        printPath(y, i, j, 1, "");
                                                        output.newLine();
                                                }
                                                else if((w1 + w2 + 1) == len[y][i][j]) {
                                                        output.write("\nUpdate path Uy=" + order[y] + " i=" + i + " j=" + j);
                                                        output.newLine();
                                                        int np = npaths[y][i][j];
                                                        npaths[y][i][j]++;
                                                        path[y][i][j][np] = p1 + "," + order[j] + "," + p2;

                                                        printLen(y, i, j, "");
                                                        output.write("   ");
                                                        printPath(y, i, j, npaths[y][i][j], "");
                                                        output.newLine();
                                                }
                                            }
                                    }
                                    output.newLine();
                                    output.newLine();
				}
			}
		}
	}

	static boolean testStableVertex(String str) {
		for (int i = 1; i < ah.length; i++)
			if (str.equals(ah[i]))
				return true;
		return false;

	}

	static void printLen(int i, int j, int k, String str) throws IOException {
		output.write(str + "len(" + order[i] + ";" + j + "," + k + ") ="
				+ len[i][j][k]);
	}

	static void printPath(int i, int j, int k, int l, String str) throws IOException {
		output.write(str + "Path(" + order[i] + ";" + j + "," + k + ") = ");
                for(int np = 0; np < l; np++)
                        output.write( "{"+ path[i][j][k][np] + "} ");
	}

	static void setMaxPair(int i, int j, int k) {
		if (mp.getLen() < len[i][j][k])
			mp = new Maxpair(i, j, k, path[i][j][k][0], len[i][j][k]);
	}
}
