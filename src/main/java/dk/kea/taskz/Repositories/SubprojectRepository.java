package dk.kea.taskz.Repositories;

import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.security.auth.Subject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubprojectRepository
{
    @Autowired
    ConnectionService connectionService;

    public List<Subproject> getAllAssociatedSubprojects(int projectId)
    {
        String getAllAssociatedSubprojectsSqlStatement = "SELECT * FROM taskz.subprojects WHERE Project_ID = " + projectId ;
        List<Subproject> subprojectList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();

        try
        {
            PreparedStatement ps = connectionService.establishConnection().prepareStatement(getAllAssociatedSubprojectsSqlStatement);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                subprojectList.add(new Subproject(rs.getInt(1),rs.getInt(3),rs.getString(2)));
            }

        }
        catch(Exception e)
        {
            System.out.println("Error happened in SubprojectRepository at getAllAssociatedSubprojects(): " + e);
        }

        return subprojectList;
    }
    
    public void insertSubProjectIntoDB(Subproject subproject) {
    	String insertSubProject = "INSERT INTO taskz.subprojects (Subproject_Name, Project_ID, Time_Spent, Subproject_Estimated_Time) VALUES (?, ?, ?, ?)";
    	
    	try {
    		PreparedStatement preparedStatement = connectionService.establishConnection().prepareStatement(insertSubProject);
    		
    		preparedStatement.setString(1, subproject.getSubprojectName());
    		preparedStatement.setInt(2, subproject.getParentProjectId());
    		preparedStatement.setInt(3, 0);
    		preparedStatement.setDouble(4, 0.0);
    		
    		preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Error in SubProjectRepository. Method: createSubProject: " + e.getMessage());
		}
	}
	
	public void deleteSubProjectFromDB(int Subproject_ID) {
    	String deleteSubProjectFromDB = "DELETE FROM subprojects WHERE Subproject_ID = ?";
    	
    	try {
			PreparedStatement preparedStatement = connectionService.establishConnection().prepareStatement(deleteSubProjectFromDB);
			preparedStatement.setInt(1, Subproject_ID);
			
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Klasse: SubprojectRepository\nMethode: deleteSubProject()\nError: " + e.getMessage());
		}
	}
	
}