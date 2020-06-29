package chrzescijanek.filip.demo.common;

@FunctionalInterface
public interface CustomValidator<T> {

    T validate(T t);

}
