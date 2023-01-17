package com.civiclife.voteservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "results")
public class Result {
    @Id
    private ResultId id;
    private int votes;
    private HashMap<String, Integer> numberOfVotesPerCandidate;

    public ResultId getId() {
        return id;
    }

    public void setId(ResultId id) {
        this.id = id;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public HashMap<String, Integer> getNumberOfVotesPerCandidate() {
        return numberOfVotesPerCandidate;
    }

    public void setNumberOfVotesPerCandidate(HashMap<String, Integer> numberOfVotesPerCandidate) {
        this.numberOfVotesPerCandidate = numberOfVotesPerCandidate;
    }

    @Override
    public String toString() {
        return "Result{" +
                ", id='" + id + '\'' +
                ", votes=" + votes +
                ", numberOfVotesPerCandidate=" + numberOfVotesPerCandidate +
                '}';
    }

}
