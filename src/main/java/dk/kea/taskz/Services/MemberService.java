package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Member;
import dk.kea.taskz.Repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MemberService
{
    @Autowired
    MemberRepository memberRepository;

    public MemberService()
    {
    }

    public boolean verifyLogin(String username, String password)
    {
        for(Member member : getAllMembers())
            if(member.getEmail().equals(username) && member.getPassword().equals(password))
            {
                return true;
            }
        
        return false;
    }

    public ArrayList<Member> getAllMembers()
    {
    	return memberRepository.getAllMembersFromDB();
    }
}
