package com.classrep.servlet;

import com.classrep.service.VotingService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/results")
public class ResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VotingService votingService = getVotingService();
        request.setAttribute("candidates", votingService.getCandidates());
        request.setAttribute("totalVotes", votingService.getTotalVotes());
        request.setAttribute("winner", votingService.getWinner());
        request.getRequestDispatcher("/results.jsp").forward(request, response);
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
}
