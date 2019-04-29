package mybatisMappers;



import java.util.List;


/*
 *  the insertOne method will be used in the test of sqlSessionTemplate with indication
 * of ExecutorType.BATCH
 *
 * the insertLOTS method will be used in the test of normal spring sqlSession. Its batch
 * operation function is based on dynamic sql using <foreach> tag.
 *
 */


public interface Mapper {

  void insertOne(String content);
  void insertLots(List<String> item);

}
