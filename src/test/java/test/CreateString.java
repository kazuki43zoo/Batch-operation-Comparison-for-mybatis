package test;

/*
 * this class is just for gnerating some contents for insert.
 * Both ways of batch insert are using very similar content
 * so what content it is doesn't matter at all because it has the same impact on both ways
 */

public class CreateString {
  static int count;
  static String getString() {
    return ++count+"";
  }
}
