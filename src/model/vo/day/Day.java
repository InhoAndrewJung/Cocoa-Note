package model.vo.day;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import model.vo.diary.*;
import util.CocoaDate;

public class Day {
	private CocoaDate date;
	private Vector<Note> notes;
	private Vector<Schedule> schedules;
	private Vector<Memo> memos;
	
	public Day() {
		super();
		this.notes = new Vector<>();
		this.schedules = new Vector<>();
		this.memos = new Vector<>();
	}

	public Day(CocoaDate date) {
		super();
		this.notes = new Vector<>();
		this.schedules = new Vector<>();
		this.memos = new Vector<>();
		this.date = date;
	}

	public CocoaDate getDate() {
		return date;
	}

	public void setDate(CocoaDate date) {
		this.date = date;
	}

	public Vector<Note> getNotes() {
		return notes;
	}

	public void setNotes(Vector<Note> notes) {
		this.notes = notes;
	}

	public Vector<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Vector<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Vector<Memo> getMemos() {
		return memos;
	}

	public void setMemos(Vector<Memo> memos) {
		this.memos = memos;
	}

	@Override
	public String toString() {
		return "Day [date=" + date + ", notes=" + notes + ", schedules=" + schedules + ", memos=" + memos + "]";
	}
	
	
}
