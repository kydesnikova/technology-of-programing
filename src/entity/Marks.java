package entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "marks", catalog = "gr")
public class Marks implements java.io.Serializable {
    private int id;

    private int mark;

    private Studentyi student ;

    private String subject;

    public  Marks(){}

    public Marks(int id, int mark, Studentyi student, String subject){
        this.id = id;
        this.mark = mark;
        this.student = student;
        this.subject = subject;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false)
    public int getId(){return this.id;}
    public void setId(int id){this.id = id;}

    @Column(name = "mark", nullable = false, length = 20)
    public int getMark(){return  this.mark;}
    public void setMark(int mark){this.mark = mark;}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentID", unique = true, nullable = true)
    public Studentyi getStudent(){return this.student;}
    public void  setStudent(Studentyi student){this.student = student;}

    @Column(name = "subject", nullable = false, length = 255)
    public String getSubject(){return this.subject;}
    public void setSubject(String subject){this.subject = subject;}
}
