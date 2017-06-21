package reminder.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import reminder.dao.ReminderDAO;
import reminder.entity.Reminder;

@Path("/Reminder")
public class ReminderService {

	private ReminderDAO dao;

	public ReminderService() {
		dao = new ReminderDAO();
	}

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllReminders(@QueryParam("status") String status, @QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate) {
		List<Reminder> allReminders = dao.getAllReminders(status, startDate, endDate);
		return allReminders.isEmpty() ? Response.status(Status.OK).entity("No reminders found").build()
				: Response.status(Status.OK).entity(allReminders).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") long id) {
		Reminder rem = dao.getById(id);
		return Response.status(rem != null ? Status.OK : Status.NOT_FOUND)
				.entity(rem != null ? rem : "No Reminder found for id => " + id).build();
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addReminder(Reminder rem) {
		long id = dao.addReminder(rem);
		return Response.status(Status.CREATED).entity("Successfully added new reminder with id => " + id).build();

	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateReminder(Reminder updatedReminder, @PathParam("id") long id) {
		boolean result = dao.updateReminder(id, updatedReminder);
		return Response.status(result ? Status.OK : Status.NOT_MODIFIED).entity(
				result ? "Successfully updated reminder for id = " + id : "Error, reminder not modified for id = " + id)
				.build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReminder(@PathParam("id") long id) {
		boolean result = dao.deleteReminder(id);
		return Response.status(result ? Status.OK : Status.FORBIDDEN)
				.entity(result ? "Successfully deleted reminder with id => " + id : "Delete failed for id => " + id)
				.build();

	}

}
