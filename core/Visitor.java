package core;

public interface Visitor {
    void visitTask(Task task);
    void visitProject(Project pr);
}
