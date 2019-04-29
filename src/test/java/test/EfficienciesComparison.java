package test;

import java.util.ArrayList;
import java.util.List;

import mybatisMappers.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.*;
import org.junit.rules.TestName;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import spring.RootConfig;

public class EfficienciesComparison {

  private static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RootConfig.class);

  private static SqlSession simpleSqlSession = ctx.getBean("simpleSqlSession", SqlSession.class);
  private static SqlSession batchSqlSession = ctx.getBean("batchSqlSession", SqlSession.class);
  private static PlatformTransactionManager transactionManager = ctx.getBean(PlatformTransactionManager.class);

  @Rule
  public TestName testName = new TestName();

  //timers
  private long start;
  private long end;

  @Before
  public void startTimer() {
    start = System.nanoTime();
  }

  @After
  public void endTimer() {
    end = System.nanoTime();
    System.out.println(String.format("%14s", testName.getMethodName()) + " : " + (end - start));
  }

  /*
   * the foreach Batch is quite efficient, it only takes second to finish the tasks
   */
  @Test
  public void foreachBatch() {
    Mapper mapper = simpleSqlSession.getMapper(Mapper.class);
    TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
    txTemplate.execute(txStatus -> {
      List<String> li = new ArrayList<>();
      for (int i = 0; i < 10000; i++) {
        li.add(CreateString.getString());
      }
      mapper.insertLots(li);
      return null;
    });
  }


  /*
   * the template BATCH is very slow. Given the same machine of test, this way of batch operation takes minutes!!
   * and i discovered that during the insert, it commits automatically everytime when it deals with one sql sentence.
   * and it can't be manually commit. see more details in  SqlSessionTemplate.
   *
   * I think automatically committing violated the rules of batch operation because too many times of commit will definately
   * slow down the insert. IS there any hidden trick to trigger the batch mode?
   *
   * what needs to be mentioned is that, this low efficiency problem raised often by many of my classmates and we don't
   * have a good way to deal with it, which is why I humbly clicked in your github page to make an issue. I look forward to
   * hearing the solution.
   *
   */
//  @Test
  public void templateBatch() {
    Mapper mapper = batchSqlSession.getMapper(Mapper.class);
    TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
    txTemplate.execute(txStatus -> {
      for (int i = 0; i < 10000; i++) {
        mapper.insertOne(CreateString.getString());
      }
      return null;
    });
  }

}
