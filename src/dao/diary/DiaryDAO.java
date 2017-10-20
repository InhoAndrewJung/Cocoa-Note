package dao.diary;

// #00011 : import change required

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javafx.scene.chart.PieChart.Data;
import jdbc.exception.RecordNotFoundException;
import sql.StringQuery;
import util.CocoaDate;
import util.DataSourceManager;
import util.IntegerRange;
import vo.day.Day;
import vo.diary.Diary;
import vo.diary.Memo;
import vo.diary.Note;
import vo.diary.Schedule;
// ------------------------------------------------ Singleton ------------------------------------------------ //
public class DiaryDAO {
	private static DiaryDAO dao = new DiaryDAO();
	private DiaryDAO() {}
	public static DiaryDAO getInstance() {
		return dao;
	}
	
	public Connection getConnection() throws SQLException{
		return DataSourceManager.getInstance().getConnection();
	}
	public void closeAll(PreparedStatement ps, Connection conn)throws SQLException{
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();
	}
	
	public void closeAll(ResultSet rs,PreparedStatement ps, Connection conn)throws SQLException{
		if(rs!=null){
			rs.close();
			closeAll(ps, conn);
		}
	}//
	
	// ------------------------------------------------ getAllNote ------------------------------------------------ //
	public Vector<Note> getAllNote(String id) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Note> n = null;
		
		try {
			conn = getConnection();
			n = new Vector<Note>();
			ps = conn.prepareStatement(StringQuery.GET_ALL_NOTE);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Note m = new Note(rs.getInt("note_no"), 			// no
						rs.getString("id"),							// id
						new CocoaDate(rs.getDate("wrt_date")), 		// writeDate
						rs.getString("content"),					// content
						new CocoaDate(rs.getDate("curr_date")),		// currentDate
						rs.getString("title"));						// title
				
				n.add(m);
			}

		} catch(SQLException e) {
			System.out.println("ERROR : [DiaryDAO]@getAllMemo : SQLException Caught !");
			e.printStackTrace();
		} finally {
			System.out.println("[DiaryDAO]@getAllMemo : Arrived finally clause");
			closeAll(rs,ps,conn);
		}
		
		return n;
	}
	// ------------------------------------------------ getAllSchedule ------------------------------------------------ //
	public Vector<Schedule> getAllSchedule(String id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Schedule> sc = null;
		String[] temp_str = {};
		
		try {
			conn = getConnection();
			sc = new Vector<Schedule>();
			ps= conn.prepareStatement(StringQuery.GET_ALL_SCHEDULE);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				sc.add(new Schedule(rs.getInt("schedule_no"), 			// no
						rs.getString("id"), 							// id
						rs.getString("title"), 							// title
						rs.getString("content"),						// content
						temp_str,										// group
						new CocoaDate(rs.getDate("start_date")),		// startDate
						new CocoaDate(rs.getDate("end_date"))));		// endDate
			}
			
		}catch(Exception e) {
			System.out.println("ERROR : [DiaryDAO]@getAllSchedule : SQLException Caught !");
			e.printStackTrace();
		}finally {
			closeAll(rs, ps, conn);
			System.out.println("[DiaryDAO]@getAllSchedule : Arrived finally clause");
		}
		return sc;
	}
	// ------------------------------------------------ getAllMemo ------------------------------------------------ //
	public Vector<Memo> getAllMemo(String id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Memo> m = null;
		
		try {
			conn = getConnection();
			m = new Vector<Memo>();
			ps= conn.prepareStatement(StringQuery.GET_ALL_MEMO);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				m.add(new Memo(rs.getInt("memo_no"), 
						id, 
						new CocoaDate(new Date(rs.getTimestamp("wrt_date").getTime())), 
						rs.getString("content")));
			}	
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(rs, ps, conn);
		}
		return m;
	}

	// ------------------------------------------------ searchNote ------------------------------------------------ //
	public Map<Integer,Note> searchNoteByKeyword(String id, String keyword) {
		Connection conn = null;
		Map<Integer,Note> result = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try { 
			conn = getConnection();
			result = new HashMap<Integer,Note>();
			ps = conn.prepareStatement(StringQuery.SEARCH_NOTE_BY_KEYWORD);
			ps.setString(1, id);
			ps.setString(2, keyword);
			ps.setString(3, keyword);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				if(rs.getString("title").toLowerCase().contains((keyword.toLowerCase())) || rs.getString("content").toLowerCase().contains(keyword.toLowerCase())) {
					if(!result.containsKey(rs.getInt("note_no")) ){			
						Note n = new Note(rs.getInt("note_no"),
											rs.getString("id"),
											new CocoaDate(new Date(rs.getTimestamp("wrt_date").getTime())),
											rs.getString("content"),
											new CocoaDate(new Date(rs.getTimestamp("curr_date").getTime())),
											rs.getString("title"));
						result.put(rs.getInt("note_no"), n);
					}
				}
			}
		} catch (SQLException e) {
			
		}
		return result;
	}
	// ------------------------------------------------ searchMemo ------------------------------------------------ //
	public Map<Integer,Memo> searchMemoByKeyword(String id, String keyword) throws SQLException{

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<Integer,Memo> memo = null;
		
		try {
			conn = getConnection();
			memo = new HashMap<Integer,Memo>();
			ps = conn.prepareStatement(StringQuery.SEARCH_MEMO_BY_KEYWORD);
			ps.setString(1, id);
			ps.setString(2, keyword);

			rs=ps.executeQuery();
			
			while(rs.next()) {
				if(rs.getString("content").toLowerCase().contains(keyword.toLowerCase())) {
					if(!memo.containsKey(rs.getInt("memo_no"))){
					Memo m = new Memo(rs.getInt("memo_no"),
											id,
											new CocoaDate(new Date(rs.getTimestamp("wrt_date").getTime())),
											rs.getString("content"));
						memo.put(rs.getInt("memo_no"), m);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			closeAll(rs, ps, conn);
		}
		
		return memo;
	}
	// ------------------------------------------------ searchSchedule ------------------------------------------------ //
	public Map<Integer,Schedule> searchScheduleByKeyword(String id, String keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer,Schedule> sc = null;
        String[] temp_str = {};
        
        try {
           conn = getConnection();
           sc = new HashMap<Integer, Schedule>();
           ps= conn.prepareStatement(StringQuery.SEARCH_SCHEDULE_BY_KEYWORD );
           ps.setString(1, id);
           ps.setString(2, keyword);
           ps.setString(3, keyword);
           rs = ps.executeQuery();
           
           while(rs.next()) {
              if(rs.getString("title").equalsIgnoreCase(keyword) || rs.getString("content").equalsIgnoreCase(keyword)) {
          if(!sc.containsKey(rs.getInt("schedule_no"))){
             Schedule s = new Schedule(rs.getInt("schedule_no"),
                   rs.getString("id"),                      // id
                       rs.getString("title"),                      // title
                       rs.getString("content"),                  // content
                       temp_str,                              // group
                       new CocoaDate(new Date(rs.getTimestamp("start_date").getTime())),      // startDate
                       new CocoaDate(new Date(rs.getTimestamp("end_date").getTime())));      // endDate
             sc.put(rs.getInt("schedule_no"), s);
          }}}
           
        }catch(Exception e) {
           e.printStackTrace();
        }finally {
           closeAll(rs, ps, conn);
         }
        return sc;
     }
	
	// ------------------------------------------------ writeDiary ------------------------------------------------ //
	public int getCurrDiaryNo() throws SQLException {
		Connection conn = null;
        PreparedStatement ps = null;
		ResultSet rs = null;
        int currNo = 0;
        try {
            conn = getConnection();
            ps= conn.prepareStatement(StringQuery.GET_CURR_DIARYNO);
            rs = ps.executeQuery();
            if(rs.next()) currNo = rs.getInt(1);
         }catch(Exception e) {
            e.printStackTrace();
         }finally {
             closeAll(rs, ps, conn);
         }
         return currNo-1;
	}
	
	public Memo writeDiary(Memo memo) throws SQLException {
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Memo rmemo = memo;
        int currNo = 0;
        try {
           conn = getConnection();
           ps= conn.prepareStatement(StringQuery.WRITE_MEMO);
           ps.setString(1, memo.getId());
           ps.setString(2, memo.getContent());
           ps.setString(3, memo.getWriteDate().getDateQuery());
           ps.executeUpdate();
           
           currNo = getCurrDiaryNo();
           rmemo.setNo(currNo);
        }catch(Exception e) {
           e.printStackTrace();
        }finally {
           closeAll(rs, ps, conn);
         }
        return rmemo;
     }
		
	// ------------------------------------------------ writeDiary ------------------------------------------------ //
	public Note writeDiary(Note note) throws SQLException {
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Note rnote = note;
        int currNo = 0;
        try {
           conn = getConnection();
           ps= conn.prepareStatement(StringQuery.WRITE_NOTE);
           //note_no, id, title, content, wrt_date, curr_date
           ps.setString(1, note.getId());
           ps.setString(2, note.getTitle());
           ps.setString(3, note.getContent());
           ps.setString(4, note.getWriteDate().getDateQuery());
           ps.setString(5, note.getWriteDate().getDateQuery());
           ps.executeUpdate();
           
           currNo = getCurrDiaryNo();
           rnote.setNo(currNo);
        }catch(Exception e) {
        	System.out.println("[DiaryDAO]@writeDiary(Note note) : ");
        	e.printStackTrace();
        }finally {
           closeAll(rs, ps, conn);
         }
        return rnote;
	}
	// ------------------------------------------------ writeDiary ------------------------------------------------ //
	public Schedule writeDiary(Schedule schedule) {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return null;
	}
	
	public ArrayList<Day> getMonthlyDiary(String id, CocoaDate date) throws SQLException{
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Day> monthlyDiary = new ArrayList<>();
        Day day = new Day();
        String lookingDate = "";
        try {
           conn = getConnection();
           for(int j=1;j<date.getStartDay();j++) monthlyDiary.add(new Day());
           for(int i=date.getRenewCal().get(Calendar.DATE);i<=date.getLastDate();i++) {
        	   lookingDate = date.getDateQuery().substring(0, 6);
        	   day = new Day(new CocoaDate(date.getYear(), date.getMonth(), i));
        	   ps= conn.prepareStatement(StringQuery.GET_DAILY_NOTE_BY_ID);
        	   if(i<10) lookingDate = lookingDate+"0"+i;
        	   else lookingDate = lookingDate+i+"";
        	   ps.setString(1, id);
        	   ps.setString(2, lookingDate);
        	   System.out.println("find for note : " + lookingDate);
        	   rs = ps.executeQuery();
        	   while(rs.next())
        		   day.getNotes().add(new Note(rs.getInt("note_no"),
        				                       id, date, 
        				                       rs.getString("content"), 
        				                       new CocoaDate(new Date(rs.getTimestamp("curr_date").getTime())),
        				                       rs.getString("title")));
        	   ps= conn.prepareStatement(StringQuery.GET_DAILY_SCHEDULE_BY_ID);
        	   ps.setString(1, id);
        	   ps.setString(2, lookingDate);

        	   System.out.println("find for schedule : " + lookingDate);
        	   rs = ps.executeQuery();
        	   while(rs.next())
        		   day.getSchedules().add(new Schedule(rs.getInt("schedule_no"), 
        				   							   id, rs.getString("title"), 
        				   							   rs.getString("content"), 
        				   							   new String[] {}, 
        				   							   new CocoaDate(new Date(rs.getTimestamp("start_date").getTime())), 
        				   						  	   new CocoaDate(new Date(rs.getTimestamp("end_date").getTime()))));
        	   monthlyDiary.add(day);
           }
        }catch(Exception e) {
           e.printStackTrace();
        }finally {
           closeAll(rs, ps, conn);
        }
		return monthlyDiary;
	}
	// ------------------------------------------------ getScheduleByNo ------------------------------------------------ //
	public Schedule getScheduleByNo(int no) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Schedule sc = null;
		String[] temp_str = {};
	
		try {
			conn = getConnection();
			ps = conn.prepareStatement(StringQuery.GET_SCHEDULE_BY_NO);
			ps.setInt(1, no);
			rs = ps.executeQuery();
			
			if(rs.next()) {
					sc = new Schedule(rs.getInt("schedule_no"),
							rs.getString("id"),
							rs.getString("title"),
							rs.getString("content"),
							temp_str,
							new CocoaDate(new Date(rs.getTimestamp("start_date").getTime())),
							new CocoaDate(new Date(rs.getTimestamp("end_date").getTime())));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, ps, conn);
		}
		return sc;
	}
	
	// ------------------------------------------------ deleteDiary ------------------------------------------------ //
	// #10004 : [Major] deleteDiary(int no) 구현방법 회의 (#9)
	// 

	public Diary deleteDiary(int no) throws SQLException{ 
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int queryResult;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(StringQuery.DELETE_SCHEDULE_BY_NO);
			queryResult = ps.executeUpdate();
			
			if(queryResult == 0) { 
				// !Schedule
				ps = conn.prepareStatement(StringQuery.DELETE_MEMO_BY_NO);
				
				if(queryResult==0){
					// !Memo
					ps = conn.prepareStatement(StringQuery.DELETE_NOTE_BY_NO);
					if(queryResult==0){
						System.out.println(no+"삭제하려는 글이 없습니다.");
					} 
				}	 
			} else {
				// queryResult == 1 --> Schedule
				System.out.println("스케줄 번호 : "+no+" 이 삭제 되었습니다.");
				
			}
			
			
			ps.setInt(1, no);
			rs = ps.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, ps, conn);
		}
		
		
		return null;
	}
	// ------------------------------------------------ getNoteList ------------------------------------------------ //
	public Vector<Note> getNoteList(String id) throws SQLException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Note> n = null;
		
		try {
			conn = getConnection();
			n = new Vector<Note>();
			ps = conn.prepareStatement(StringQuery.GET_ALL_NOTE);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Note m = new Note(rs.getInt("note_no"), 			// no
						rs.getString("id"),							// id
						new CocoaDate(rs.getDate("wrt_date")), 		// writeDate
						rs.getString("content"),					// content
						new CocoaDate(rs.getDate("curr_date")),		// currentDate
						rs.getString("title"));						// title
				
				n.add(m);
			}

		} catch(SQLException e) {
			System.out.println("ERROR : [DiaryDAO]@getNoteList : SQLException Caught !");
			e.printStackTrace();
		} finally {
			System.out.println("[DiaryDAO]@getNoteList : Arrived finally clause");
			closeAll(rs,ps,conn);
		}
		
		return n;
	}
	
	// ------------------------------------------------ getNoteByNo ------------------------------------------------ //
	public Note getNoteByNo(int no) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Note note = new Note();
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(StringQuery.GET_NOTE_BY_NO);
			ps.setInt(1, no);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				note = new Note(rs.getInt("note_no"), 			// no
						rs.getString("id"),							// id
						new CocoaDate(rs.getDate("wrt_date")), 		// writeDate
						rs.getString("content"),					// content
						new CocoaDate(rs.getDate("curr_date")),		// currentDate
						rs.getString("title"));						// title
				
			}

		} catch(SQLException e) {
			System.out.println("ERROR : [DiaryDAO]@getNoteByNo : SQLException Caught !");
			e.printStackTrace();
		} finally {
			System.out.println("[DiaryDAO]@getNoteByNo : Arrived finally clause");
			closeAll(rs,ps,conn);
		}
		
		return note;
	}
	// ------------------------------------------------ updateNote ------------------------------------------------ //
	public Note updateNote(int no, String title, String content) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		Note note = null;
		
		try {
			conn = getConnection();
			
			// 1. Search Note by No.
			note = getNoteByNo(no);
			if(note == null) throw new RecordNotFoundException("[DiaryDAO]@updateNote : No Such Note Found !");
			else System.out.println("[DiaryDAO]@updateNote : Found note with no : " + no);
			
			// 2. Excute query (sql.StringQuery.UPDATE_NOTE)
			ps = conn.prepareStatement(StringQuery.UPDATE_NOTE);
			ps.setString(1, title);
			ps.setString(2, content);
			ps.setInt(3, no);
			
			ps.executeUpdate();
			System.out.println("[DiaryDAO]@updateNote : Update Complete for Note no : " + no);
			
			
			// To return note instance
			note.setTitle(title);
			note.setContent(content);
				
		} catch(SQLException e) {
			System.out.println("[DiaryDAO]@updateNote : Update Failed for Note.no : " + no);
			System.out.println("[DiaryDAO]@updateNote : SQLException !");
			e.printStackTrace();
		} finally {
			closeAll(ps, conn);
		}
		return note;
	}
	// ------------------------------------------------ updateSchedule ------------------------------------------------ //
	public Schedule updateSchedule(int no, String title, String content, CocoaDate start_date, CocoaDate end_date) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		Schedule schedule = null;
		
		try{
			conn = getConnection();
			
			// 1. Find Schedule by No.
			schedule = getScheduleByNo(no);
			if(schedule == null) throw new RecordNotFoundException("[DiaryDAO]@updateSchedule : No Such Schedule Found with no : " + no);
			else System.out.println("[DiaryDAO]@updateSchedule : Found schedule with no : " + no);
			
			// 2. Execute query (sql.StringQuery.UPDATE_SCHEDULE)
			ps = conn.prepareStatement(StringQuery.UPDATE_SCHEDULE);
			ps.setString(1, title);
			ps.setString(2, content);
			ps.setString(3, start_date.getDateQuery());
			ps.setString(4, end_date.getDateQuery());
			ps.setInt(5, no);
			
			ps.executeUpdate();
			System.out.println("[DiaryDAO]@updateSchedule : Update Complete for Schedule.no : " + no);
			
			schedule.setTitle(title);
			schedule.setContent(content);
			schedule.setStartDate(start_date);
			schedule.setEndDate(end_date);
			
		} catch(SQLException e) {
			System.out.println("[DiaryDAO]@updateSchedule : Update Failed for Schedule.no : " + no);
			System.out.println("[DiaryDAO]@updateSchedule : SQLException !");
			e.printStackTrace();
		} finally {
			closeAll(ps, conn);
		}
		
		
		return schedule;
	}
	// ------------------------------------------------ getDay  ------------------------------------------------ //
	public Day getDay(String id, int year, int month, int date) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		CocoaDate searchDate = null;
		Day day = null;
		Vector<Note> notes;
		Vector<Schedule> schedules;
		
		try {
			conn = getConnection();
		
			// 1. Find all Diary Items for the member.
			searchDate = new CocoaDate(year, month, date);
			day = new Day(searchDate);
			notes = getAllNote(id);
			schedules = getAllSchedule(id);
			
			if( notes == null && schedules == null) {
				System.out.println("[DiaryDAO]@getDay : No Notes Found for Member id : " + id);
			} else {
				// in case member has either notes or schedules
				for(Note note : notes) {
					if(searchDate.getYear() == note.getWriteDate().getYear() && 
							searchDate.getMonth() == note.getWriteDate().getMonth() && 
							searchDate.getDate() == note.getWriteDate().getDate()) {
						
						day.getNotes().add(note);						
					}
				}
				
				for(Schedule schedule : schedules) {
					if(IntegerRange.betweenInclusive(searchDate.getYear(), schedule.getStartDate().getYear(), schedule.getEndDate().getYear()))
						if(IntegerRange.betweenInclusive(searchDate.getMonth(), schedule.getStartDate().getMonth(), schedule.getEndDate().getMonth()))
							if(IntegerRange.betweenInclusive(searchDate.getDate(), schedule.getStartDate().getDate(), schedule.getEndDate().getDate()))
								day.getSchedules().add(schedule);
				}
			}
			
		} catch(SQLException e) {
			System.out.println("[DiaryDAO]@getDay : SQLException !");
		} finally {
			conn.close();
		}
		
		return day;
	}
}
