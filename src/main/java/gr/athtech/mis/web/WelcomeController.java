package gr.athtech.mis.web;

import gr.athtech.mis.model.Group;
import gr.athtech.mis.model.PaidVisit;
import gr.athtech.mis.model.ScheduledVisit;
import gr.athtech.mis.model.User;
import gr.athtech.mis.repository.DoctorRepository;
import gr.athtech.mis.repository.GroupRepository;
import gr.athtech.mis.repository.PaidVisitRepository;
import gr.athtech.mis.repository.ScheduledVisitRepository;
import gr.athtech.mis.repository.UserRepository;
import gr.athtech.mis.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduledVisitRepository scheduledVisitRepository;
    @Autowired
    private PaidVisitRepository paidVisitRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private AuthService authService;

    /**
     * Return the main dashboard view
     *
     * @param request
     * @param principal
     * @param model
     * @return String
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Principal principal, Model model) {

        List<ScheduledVisit> scheduledVisitsList = scheduledVisitRepository.findAll();

        int doctors = doctorRepository.findAll().size();
        int medicalVisitors = userRepository.getMedicalVisitors().size();
        int scheduledVisits = scheduledVisitsList.size();
        int paidVisits = paidVisitRepository.findAll().size();

        model.addAttribute("doctors", doctors);
        model.addAttribute("medicalVisitors", medicalVisitors);
        model.addAttribute("scheduledVisits", scheduledVisits);
        model.addAttribute("paidVisits", paidVisits);

        Long id = authService.getId();

        User user = userRepository.findOne(id);

        //for individual visits
        List<ScheduledVisit> newVisits = scheduledVisitRepository.getUsersFromCurrentCycle(id);
        List<PaidVisit> paidVisitsList = paidVisitRepository.getAllUserVisitsByCurrentCycle(id);

        //For group visits
        List<Group> leaders = groupRepository.findByLeader(user);
        List<Group> members = groupRepository.findByUserId(id);

        if (leaders.isEmpty()) {

            Long memberId = groupRepository.findByUserIdUnique(id);
            List<ScheduledVisit> newGroupVisits = scheduledVisitRepository.findRelatedMembersId(memberId);
            model.addAttribute("newGroupVisits", newGroupVisits);
            List<PaidVisit> groupVisits = paidVisitRepository.findRelatedMembersId(memberId);
            model.addAttribute("groupVisits", groupVisits);
        } else if (members.isEmpty()) {

            List<ScheduledVisit> newGroupVisits = scheduledVisitRepository.getGroupsFromCurrentCycle(id);
            model.addAttribute("newGroupVisits", newGroupVisits);
            List<PaidVisit> groupVisits = paidVisitRepository.getAllGroupVisitsByCurrentCycle(id);
            model.addAttribute("groupVisits", groupVisits);
        } else {

            List<ScheduledVisit> newGroupVisits = scheduledVisitRepository.findByMemberAndLeader(id);
            model.addAttribute("newGroupVisits", newGroupVisits);
            List<PaidVisit> groupVisits = paidVisitRepository.findEitherMemberOrLeader(id);
            model.addAttribute("groupVisits", groupVisits);
        }

        model.addAttribute("newVisits", newVisits);
        model.addAttribute("paidVisitsList", paidVisitsList);

        return "index";
    }

    /**
     * Return the calendar data
     *
     * @param request
     * @param principal
     * @param model
     * @return Map<String, String>
     */
    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> calendar(HttpServletRequest request, Principal principal, Model model) {

        List<ScheduledVisit> scheduledVisitsList = scheduledVisitRepository.findAll();
        //calendari
        Map<String, String> calendar = new HashMap<>();

        for (ScheduledVisit sch : scheduledVisitsList) {
            for (PaidVisit paid : sch.getPaidVisits()) {
                calendar.put(sch.getDoctor().getFirstName() + " " + sch.getDoctor().getLastName(), paid.getDate().toString());
            }
        }

        return calendar;
    }

}
