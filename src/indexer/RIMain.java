/**
 * 
 */
package indexer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * @author Yayang Tian
 * http://cis.upenn.edu/~yaytian
 */
public class RIMain {
	public static void main(String[] args) throws Exception {
	    Configuration conf = new Configuration();
	    conf.setInt("io.sort.mb", 64);
	    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	    if (otherArgs.length != 2) {
	      System.err.println("Usage: RIMain.jar <in> <out>");
	      System.exit(2);
	    }
	    Job job = new Job(conf, "RIMain");
	    job.setInputFormatClass(MyFileInputFormat.class);
	    job.setJarByClass(RIMain.class);
	    job.setMapperClass(RIMapper.class);
	    job.setReducerClass(RIReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(Text.class);
	    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	  }
}
