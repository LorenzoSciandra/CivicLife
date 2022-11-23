package com.civiclife.backend.model;
import javax.persistence.*;

@Entity
@Table (name = "user")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "first_name")
        private String first_name;

        @Column(name = "age")
        private int age;

        public User() {
        }

        public User(String first_name, int age) {
            this.first_name = first_name;
            this.age = age;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return first_name;
        }

        public void setName(String first_name) {
            this.first_name = first_name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
}
