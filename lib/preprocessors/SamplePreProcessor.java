package preprocessors;

import org.rosuda.JRI.Rengine;

/**
 * Created by AMIT on 21/10/14.
 */
public class SamplePreProcessor
{
    public static void main(String[] args)
    {
        // new R-engine
        Rengine re = new Rengine(new String[]{"--vanilla"}, false, null);
        if (!re.waitForR())
        {
            System.out.println("Cannot load R");
            return;
        }

        // print a random number from uniform distribution
        System.out.println(re.eval("x <- 1:10\n" +
                "z <- NULL\n" +
                "for(i in seq(along=x)) { \n" +
                "    if(x[i] < 5) { \n" +
                "        z <- c(z, x[i] - 1)  \n" +
                "    } else { \n" +
                "        z <- c(z, x[i] / x[i])  \n" +
                "    } \n" +
                "}\n" +
                "z"));

        re.end();
    }
}
