package com.classrep.service;

import com.classrep.model.Candidate;
import com.classrep.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class VotingService {
    private final List<Candidate> candidates;
    private final Set<String> votedRegisterNumbers;

    public VotingService() {
        this.candidates = new ArrayList<>();
        this.votedRegisterNumbers = new HashSet<>();
        addDefaultCandidates();
    }

    private void addDefaultCandidates() {
        candidates.add(new Candidate(1, "Ananya Sharma", "Discipline, teamwork, and progress."));
        candidates.add(new Candidate(2, "Rahul Kumar", "A voice for every student."));
        candidates.add(new Candidate(3, "Meera Nair", "Better communication, better class."));
    }

    public synchronized boolean castVote(Student student, int candidateId) {
        String registerNumber = student.getRegisterNumber().trim().toUpperCase();

        if (votedRegisterNumbers.contains(registerNumber)) {
            return false;
        }

        Optional<Candidate> selectedCandidate = candidates.stream()
                .filter(candidate -> candidate.getId() == candidateId)
                .findFirst();

        if (!selectedCandidate.isPresent()) {
            return false;
        }

        selectedCandidate.get().addVote();
        votedRegisterNumbers.add(registerNumber);
        return true;
    }

    public List<Candidate> getCandidates() {
        return Collections.unmodifiableList(candidates);
    }

    public int getTotalVotes() {
        return candidates.stream().mapToInt(Candidate::getVoteCount).sum();
    }

    public Candidate getWinner() {
        return candidates.stream()
                .max(Comparator.comparingInt(Candidate::getVoteCount))
                .orElse(null);
    }
}
