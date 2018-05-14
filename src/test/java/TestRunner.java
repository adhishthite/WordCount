import java.util.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        List<Result> resultList = new ArrayList<Result>();
        Boolean hasError = false;
        
        resultList.add(JUnitCore.runClasses(TestWordOps.class));
        resultList.add(JUnitCore.runClasses(TestWordOpsThread.class));
		
        for(Result result : resultList) {
            for (Failure failure : result.getFailures()) {
                hasError = true;
                System.out.println(failure.toString());
            }
        }
        
        if(!hasError) {
            System.out.println("\nTest Run Successful!");
        }
    }
}
