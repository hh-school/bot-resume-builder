package ru.hh.resumebuilderbot.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "node")
public class Node {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "classpath", nullable = false, unique = true, length = 80)
    private String classpath;
    @Column(name = "data", nullable = false, length = 1024)
    private String data;

    public Node() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Node)) {
            return false;
        }
        Node node = (Node) o;
        return Objects.equals(id, node.id) &&
                Objects.equals(classpath, node.classpath) &&
                Objects.equals(data, node.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classpath, data);
    }

    @Override
    public String toString() {
        return String.format("Node{id=%d, classpath='%s', data='%s'}", id, classpath, data);
    }
}
