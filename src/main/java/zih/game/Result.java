package zih.game;

import java.util.Objects;

public class Result {

  private final boolean success;
  private final String message;

  // constructor takes only a message as input
  // assumes the result was not successful
  // manually create a failure result with a message,
  // without manually specifying the false

  // It calls another constructor of the same class using this(false, message);.
  // Known as constructor chaining — it’s saying:
  // “Use the other constructor that takes both a boolean and a String,
  // and pass in false and the given message.”
  public Result(String message){
    this(message, false);
  }

  public Result(String message, boolean success) {
    this.message = message;
    this.success = success;
  }

  public boolean isSuccess(){ return success; }

  public String getMessage() {
    return message;
  }

  // checks whether two Result objects are equal by value
  // their success fields are same and their message fields
  // are either both null or equal strings
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Result result = (Result) o;
    return success == result.success && Objects.equals(message, result.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, message);
  }

  @Override
  public String toString() {
    return "Result{" +
            "success=" + success +
            ", message='" + message + '\'' +
            '}';
  }
}
