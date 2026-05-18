package com.classrep.servlet;

import com.classrep.model.Student;
import com.classrep.service.VotingService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VotingService votingService = getVotingService();
        request.setAttribute("candidates", votingService.getCandidates());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String registerNumber = request.getParameter("registerNumber");
        String studentName = request.getParameter("studentName");
        String candidateIdValue = request.getParameter("candidateId");

        if (isBlank(registerNumber) || isBlank(studentName) || isBlank(candidateIdValue)) {
            request.setAttribute("errorMessage", "Please enter student details and select a candidate.");
            doGet(request, response);
            return;
        }

        int candidateId;
        try {
            candidateId = Integer.parseInt(candidateIdValue);
        } catch (NumberFormatException exception) {
            request.setAttribute("errorMessage", "Invalid candidate selected.");
            doGet(request, response);
            return;
        }

        Student student = new Student(registerNumber, studentName);
        boolean voteRecorded = getVotingService().castVote(student, candidateId);

        if (voteRecorded) {
            response.sendRedirect(request.getContextPath() + "/results");
        } else {
            request.setAttribute("errorMessage", "This register number has already voted or the candidate is invalid.");
            doGet(request, response);
        }
    }

    private VotingService getVotingService() {
        ServletContext context = getServletContext();
        VotingService votingService = (VotingService) context.getAttribute("votingService");

        if (votingService == null) {
            synchronized (context) {
                votingService = (VotingService) context.getAttribute("votingService");
                if (votingService == null) {
                    votingService = new VotingService();
                    context.setAttribute("votingService", votingService);
                }
            }
        }

        return votingService;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
