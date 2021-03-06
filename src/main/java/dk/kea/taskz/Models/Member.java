package dk.kea.taskz.Models;

import dk.kea.taskz.Services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

/**(FMP, OVO, RBP, CMB)
 * Blueprint for the object/model Member, with getters/setters
 * Have constructor overloads because we need constructors
 * with different parameters
 */

public class Member
{
    private int memberId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
	private String competence;
	private String jobTitle;

    public Member()
    {
    }

	public Member(int memberId, String email, String password, String firstName, String lastName, String competences, String jobTitle)
    {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.competence = competences;
        this.jobTitle = jobTitle;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

	public String getCompetence() {
		return competence;
	}

	public String getJobTitle() {
		return jobTitle;
	}


}
