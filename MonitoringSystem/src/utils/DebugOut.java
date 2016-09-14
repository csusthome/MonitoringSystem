package utils;
/**
 * 调试输出
 * @author 林辉
 *
 */
public class DebugOut {

	private static boolean DEBUG = true;	 //DEBUG为true时在控制台输出信息
	
	public static void print(String arg){
		if(DEBUG == true){
			System.out.print(arg);
		}
	}
	
	public static void println(String arg){
		if(DEBUG == true){
			System.out.println(arg);
		}
	}
	
}
