package co.edu.unbosque.model;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.schedule.ScheduleEntryMoveEvent;
import org.primefaces.event.schedule.ScheduleEntryResizeEvent;
import org.primefaces.event.schedule.ScheduleRangeEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleDisplayMode;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import co.edu.unbosque.service.ExtenderService;
import co.edu.unbosque.service.ExtenderService.ExtenderExample;

@Named("cronogramaBean")
@ViewScoped
public class CronogramaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ExtenderService extenderService;

	private ScheduleModel eventModel;
	private ScheduleModel lazyEventModel;
	private ScheduleEvent<?> event = new DefaultScheduleEvent<>();

	private boolean slotEventOverlap = true;
	private boolean showWeekNumbers = false;
	private boolean showHeader = true;
	private boolean draggable = true;
	private boolean resizable = true;
	private boolean selectable = false;
	private boolean showWeekends = true;
	private boolean tooltip = true;
	private boolean allDaySlot = true;
	private boolean rtl = false;

	private double aspectRatio = Double.MIN_VALUE;

	private String leftHeaderTemplate = "prev,next today";
	private String centerHeaderTemplate = "title";
	private String rightHeaderTemplate = "dayGridMonth,timeGridWeek,timeGridDay,listYear";
	private String nextDayThreshold = "09:00:00";
	private String weekNumberCalculation = "local";
	private String weekNumberCalculator = "date.getTime()";
	private String displayEventEnd;
	private String timeFormat;
	private String slotDuration = "00:30:00";
	private String slotLabelInterval;
	private String slotLabelFormat = "HH:mm";
	private String scrollTime = "06:00:00";
	private String minTime = "04:00:00";
	private String maxTime = "20:00:00";
	private String locale = "es";
	private String serverTimeZone = ZoneId.systemDefault().toString();
	private String timeZone = "";
	private String clientTimeZone = "local";
	private String columnHeaderFormat = "";
	private String view = "timeGridWeek";
	private String height = "auto";

	private String extenderCode = "function(cfg){}"; // no-op por defecto
	private String selectedExtenderExample = "tooltips"; // asegúrate que exista en el .properties

	private Map<String, ExtenderExample> extenderExamples;

	@PostConstruct
	public void init() {
		// Modelo base
		eventModel = new DefaultScheduleModel();
		addEvents2EventModel(LocalDateTime.now());
		addEvents2EventModel(LocalDateTime.now().minusMonths(6));

		// Modelo perezoso (ejemplo)
		lazyEventModel = new LazyScheduleModel() {
			@Override
			public void loadEvents(LocalDateTime start, LocalDateTime end) {
				for (int i = 1; i <= 5; i++) {
					LocalDateTime random = getRandomDateTime(start);
					addEvent(DefaultScheduleEvent.builder().title("Lazy Event " + i).startDate(random)
							.endDate(random.plusHours(3)).build());
				}
			}
		};

		// Carga de extenders (robusto)
		if (extenderService != null) {
			extenderExamples = extenderService.createExtenderExamples();
		} else {
			extenderExamples = new HashMap<>();
			System.err.println("[CronogramaDTO] extenderService es null (¿falta WEB-INF/beans.xml?)");
		}

		if (extenderExamples == null || extenderExamples.isEmpty()) {
			extenderCode = "function(cfg){}"; // no-op
		} else {
			ExtenderExample ex = getExtenderExample();
			extenderCode = (ex != null && ex.getValue() != null && !ex.getValue().trim().isEmpty()) ? ex.getValue()
					: "function(cfg){}";
		}
	}

	private void addEvents2EventModel(LocalDateTime referenceDate) {
		DefaultScheduleEvent<?> ev = DefaultScheduleEvent.builder().title("Champions League Match")
				.startDate(previousDay8Pm(referenceDate)).endDate(previousDay11Pm(referenceDate))
				.description("Team A vs. Team B").url("https://www.uefa.com/uefachampionsleague/").borderColor("orange")
				.build();
		eventModel.addEvent(ev);

		ev = DefaultScheduleEvent.builder().startDate(referenceDate.minusDays(6)).endDate(referenceDate.minusDays(3))
				.overlapAllowed(true).editable(false).resizable(false).display(ScheduleDisplayMode.BACKGROUND)
				.backgroundColor("lightgreen").build();
		eventModel.addEvent(ev);

		ev = DefaultScheduleEvent.builder().title("Birthday Party").startDate(today1Pm(referenceDate))
				.endDate(today6Pm(referenceDate)).description("Aragon").overlapAllowed(true).borderColor("#CB4335")
				.build();
		eventModel.addEvent(ev);

		ev = DefaultScheduleEvent.builder().title("Breakfast at Tiffanys (always resizable)")
				.startDate(nextDay9Am(referenceDate)).endDate(nextDay11Am(referenceDate)).description("all you can eat")
				.overlapAllowed(true).resizable(true).borderColor("#27AE60").build();
		eventModel.addEvent(ev);

		ev = DefaultScheduleEvent.builder().title("Plant the new garden stuff (always draggable)")
				.startDate(theDayAfter3Pm(referenceDate)).endDate(fourDaysLater3pm(referenceDate))
				.description("Trees, flowers, ...").draggable(true).borderColor("#27AE60").build();
		eventModel.addEvent(ev);

		DefaultScheduleEvent<?> allDay = DefaultScheduleEvent.builder().title("Holidays (AllDay)")
				.startDate(sevenDaysLater0am(referenceDate)).endDate(eightDaysLater0am(referenceDate)) // FIX: ahora +8
																										// días
				.description("sleep as long as you want").borderColor("#27AE60").allDay(true).build();
		eventModel.addEvent(allDay);
	}

	// ====== Getters para el xhtml ======
	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	public ScheduleEvent<?> getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent<?> event) {
		this.event = event;
	}

	// ====== Acciones del schedule ======
	public void addEvent() {
		if (event.isAllDay() && event.getStartDate() != null && event.getEndDate() != null) {
			if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
				event.setEndDate(event.getEndDate().plusDays(1));
			}
		}
		if (event.getId() == null) {
			eventModel.addEvent(event);
		} else {
			eventModel.updateEvent(event);
		}
		event = new DefaultScheduleEvent<>();
	}

	public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
		event = selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
		event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject())
				.endDate(selectEvent.getObject().plusHours(1)).build();
	}

	public void onEventMove(ScheduleEntryMoveEvent e) {
		addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Delta:" + e.getDeltaAsDuration()));
	}

	public void onEventResize(ScheduleEntryResizeEvent e) {
		addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized",
				"Start-Delta:" + e.getDeltaStartAsDuration() + ", End-Delta: " + e.getDeltaEndAsDuration()));
	}

	public void onRangeSelect(ScheduleRangeEvent e) {
		addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Range selected",
				"Start-Date:" + e.getStartDate() + ", End-Date: " + e.getEndDate()));
	}

	public void onEventDelete() {
		String eventId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("eventId");
		if (eventId != null) {
			ScheduleEvent<?> ev = eventModel.getEvent(eventId);
			if (ev != null)
				eventModel.deleteEvent(ev);
		}
	}

	public void onExtenderExampleSelect(AjaxBehaviorEvent e) {
		ExtenderExample example = getExtenderExample();
		if (!"custom".equals(selectedExtenderExample) && example != null) {
			if (example.getDetails() != null && !example.getDetails().isEmpty()) {
				FacesMessage msg = new FacesMessage(example.getName(), example.getDetails());
				FacesContext.getCurrentInstance().addMessage(e.getComponent().getClientId(), msg);
			}
			this.extenderCode = example.getValue();
		}
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	// ====== Config visual ======
	public boolean isShowWeekends() {
		return showWeekends;
	}

	public void setShowWeekends(boolean showWeekends) {
		this.showWeekends = showWeekends;
	}

	public boolean isSlotEventOverlap() {
		return slotEventOverlap;
	}

	public void setSlotEventOverlap(boolean slotEventOverlap) {
		this.slotEventOverlap = slotEventOverlap;
	}

	public boolean isShowWeekNumbers() {
		return showWeekNumbers;
	}

	public void setShowWeekNumbers(boolean showWeekNumbers) {
		this.showWeekNumbers = showWeekNumbers;
	}

	public boolean isShowHeader() {
		return showHeader;
	}

	public void setShowHeader(boolean showHeader) {
		this.showHeader = showHeader;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public boolean isTooltip() {
		return tooltip;
	}

	public void setTooltip(boolean tooltip) {
		this.tooltip = tooltip;
	}

	public boolean isRtl() {
		return rtl;
	}

	public void setRtl(boolean rtl) {
		this.rtl = rtl;
	}

	public boolean isAllDaySlot() {
		return allDaySlot;
	}

	public void setAllDaySlot(boolean allDaySlot) {
		this.allDaySlot = allDaySlot;
	}

	public double getAspectRatio() {
		return aspectRatio == 0 ? Double.MIN_VALUE : aspectRatio;
	}

	public void setAspectRatio(double aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public String getLeftHeaderTemplate() {
		return leftHeaderTemplate;
	}

	public void setLeftHeaderTemplate(String leftHeaderTemplate) {
		this.leftHeaderTemplate = leftHeaderTemplate;
	}

	public String getCenterHeaderTemplate() {
		return centerHeaderTemplate;
	}

	public void setCenterHeaderTemplate(String centerHeaderTemplate) {
		this.centerHeaderTemplate = centerHeaderTemplate;
	}

	public String getRightHeaderTemplate() {
		return rightHeaderTemplate;
	}

	public void setRightHeaderTemplate(String rightHeaderTemplate) {
		this.rightHeaderTemplate = rightHeaderTemplate;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getNextDayThreshold() {
		return nextDayThreshold;
	}

	public void setNextDayThreshold(String nextDayThreshold) {
		this.nextDayThreshold = nextDayThreshold;
	}

	public String getWeekNumberCalculation() {
		return weekNumberCalculation;
	}

	public void setWeekNumberCalculation(String weekNumberCalculation) {
		this.weekNumberCalculation = weekNumberCalculation;
	}

	public String getWeekNumberCalculator() {
		return weekNumberCalculator;
	}

	public void setWeekNumberCalculator(String weekNumberCalculator) {
		this.weekNumberCalculator = weekNumberCalculator;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getSlotDuration() {
		return slotDuration;
	}

	public void setSlotDuration(String slotDuration) {
		this.slotDuration = slotDuration;
	}

	public String getSlotLabelInterval() {
		return slotLabelInterval;
	}

	public void setSlotLabelInterval(String slotLabelInterval) {
		this.slotLabelInterval = slotLabelInterval;
	}

	public String getSlotLabelFormat() {
		return slotLabelFormat;
	}

	public void setSlotLabelFormat(String slotLabelFormat) {
		this.slotLabelFormat = slotLabelFormat;
	}

	public String getDisplayEventEnd() {
		return displayEventEnd;
	}

	public void setDisplayEventEnd(String displayEventEnd) {
		this.displayEventEnd = displayEventEnd;
	}

	public String getScrollTime() {
		return scrollTime;
	}

	public void setScrollTime(String scrollTime) {
		this.scrollTime = scrollTime;
	}

	public String getMinTime() {
		return minTime;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public String getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getClientTimeZone() {
		return clientTimeZone;
	}

	public void setClientTimeZone(String clientTimeZone) {
		this.clientTimeZone = clientTimeZone;
	}

	public String getColumnHeaderFormat() {
		return columnHeaderFormat;
	}

	public void setColumnHeaderFormat(String columnHeaderFormat) {
		this.columnHeaderFormat = columnHeaderFormat;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	// ====== Extenders ======
	public Map<String, ExtenderExample> getExtenderExamplesMap() {
		return extenderExamples;
	}

	public List<SelectItem> getExtenderExamples() {
		return extenderExamples.values().stream().sorted(Comparator.comparing(ExtenderExample::getName))
				.map(example -> new SelectItem(example.getKey(), example.getName())).collect(Collectors.toList());
	}

	public ExtenderExample getExtenderExample() {
		return (extenderExamples != null) ? extenderExamples.get(selectedExtenderExample) : null;
	}

	public String getSelectedExtenderExample() {
		return selectedExtenderExample;
	}

	public void setSelectedExtenderExample(String selectedExtenderExample) {
		this.selectedExtenderExample = selectedExtenderExample;
	}

	public String getExtenderCode() {
		return extenderCode;
	}

	public void setExtenderCode(String extenderCode) {
		this.extenderCode = extenderCode;
	}

	public ExtenderService getScheduleExtenderService() {
		return extenderService;
	}

	public void setScheduleExtenderService(ExtenderService extenderService) {
		this.extenderService = extenderService;
	}

	public String getServerTimeZone() {
		return serverTimeZone;
	}

	public void setServerTimeZone(String serverTimeZone) {
		this.serverTimeZone = serverTimeZone;
	}

	// ====== Helpers de fechas ======
	public LocalDate getInitialDate() {
		return LocalDate.now().plusDays(1);
	}

	public LocalDateTime getRandomDateTime(LocalDateTime base) {
		LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
		return dateTime.plusDays(((int) (Math.random() * 30)));
	}

	private LocalDateTime previousDay8Pm(LocalDateTime referenceDate) {
		return referenceDate.minusDays(1).withHour(20).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime previousDay11Pm(LocalDateTime referenceDate) {
		return referenceDate.minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime today1Pm(LocalDateTime referenceDate) {
		return referenceDate.withHour(13).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime theDayAfter3Pm(LocalDateTime referenceDate) {
		return referenceDate.plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime today6Pm(LocalDateTime referenceDate) {
		return referenceDate.withHour(18).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime nextDay9Am(LocalDateTime referenceDate) {
		return referenceDate.plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime nextDay11Am(LocalDateTime referenceDate) {
		return referenceDate.plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime fourDaysLater3pm(LocalDateTime referenceDate) {
		return referenceDate.plusDays(4).withHour(15).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime sevenDaysLater0am(LocalDateTime referenceDate) {
		return referenceDate.plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime eightDaysLater0am(LocalDateTime referenceDate) {
		return referenceDate.plusDays(8).withHour(0).withMinute(0).withSecond(0).withNano(0);
	}
}
