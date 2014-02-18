import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ReadJson {
	static HashMap<String, Set<String>> jobskill_set = new HashMap<String, Set<String>>();
	
	public static void main(String args[]) throws IOException {
		String filepath = "1w.json";
		String tmp = null;
		
		BufferedReader br = new BufferedReader(new FileReader(new File(filepath)));

		while ((tmp = br.readLine()) != null) {

			 String jobtitle=null; 
			 int begin=tmp.indexOf("'job_position': '"); 
			 int end=tmp.indexOf("',", begin); 
			 jobtitle=tmp.substring(begin+17,end);
			 
			String skilllist = null;
			int sbegin = tmp.indexOf("'skill_list': ['");
			int send = tmp.indexOf("'],", sbegin);

			// if skilllist is null in text
			if (sbegin == -1)continue;
			Set<String> skillset= new HashSet<String>();
			skilllist = tmp.substring(sbegin + 16, send);
			if (skilllist != null) {
				// construct skill set
				String[] skills = skilllist.split("', '");
				for (String s : skills) {
					// 如果是内部嵌套
					if (s.contains("/")) {
						skillset.addAll( Arrays.asList(s.split("/")));
					}
					if (s.contains("\\")) {
						String[] st=s.split("\\\\"); // ......无语了
						skillset.addAll( Arrays.asList(st));
					}
					else {
						skillset.add(s);
					}
				}				
			}
			
			if(jobskill_set.containsKey(jobtitle)){
				skillset.addAll(jobskill_set. get(jobtitle));
				jobskill_set.put(jobtitle, skillset);
			}else{
				jobskill_set.put(jobtitle, skillset);
			}
			
		}
		br.close();
		// release memory
		br = null;
		System.out.println("label construct end");
		int test =similarity("平面设计","插画设计");
	}
	static int similarity(String jobtitleA, String jobtitleB){
		Set askills=jobskill_set.get(jobtitleA);
		Set bskills=jobskill_set.get(jobtitleB);
		if((askills==null)||(bskills==null)){
			return 0;
		}
		if(askills.retainAll(bskills)){
			return askills.size();
		}
		//just another test line
		
		
		
		//add a second line
		else		return 0;
	}

}
