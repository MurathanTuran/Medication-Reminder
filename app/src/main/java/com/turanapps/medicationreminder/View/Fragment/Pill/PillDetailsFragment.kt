package com.turanapps.medicationreminder.View.Fragment.Pill

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.turanapps.medicationreminder.Error.ObserveErrors
import com.turanapps.medicationreminder.Model.Pill
import com.turanapps.medicationreminder.Model.PillAlarm
import com.turanapps.medicationreminder.R
import com.turanapps.medicationreminder.Util.AlarmReceiver
import com.turanapps.medicationreminder.ViewModel.Fragment.Pill.PillDetailsViewModel
import com.turanapps.medicationreminder.databinding.FragmentPillDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.util.Calendar

@AndroidEntryPoint
class PillDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPillDetailsBinding

    private val viewModel: PillDetailsViewModel by viewModels()

    private var spinnerPosition: Int = 0

    val calendar = Calendar.getInstance()

    private var firstTimeHour: Int? = null
    private var firstTimeMinute: Int? = null

    private var secondTimeHour: Int? = null
    private var secondTimeMinute: Int? = null

    private var thirdTimeHour: Int? = null
    private var thirdTimeMinute: Int? = null

    private var fourthTimeHour: Int? = null
    private var fourthTimeMinute: Int? = null

    private var fifthTimeHour: Int? = null
    private var fifthTimeMinute: Int? = null

    private var timeInMillisArray = arrayListOf<Long?>(null, null, null, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPillDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObserveErrors.observe(viewLifecycleOwner, requireContext(),
            viewModel.insertPillErrorEntity, viewModel.insertPillAlarmErrorEntity)

        setupSpinner()

        setTime()

        setPill()

        observeReadyToGoPillFragment()

    }

    private fun setupSpinner(){
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.howManyTimes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.howManyTimesSpinner.adapter = adapter
        }

        binding.howManyTimesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0 -> {
                        spinnerPosition = 0

                        binding.setFirstButton.visibility = View.VISIBLE
                        binding.firstTimeText.visibility = View.VISIBLE

                        binding.setSecondButton.visibility = View.GONE
                        binding.secondTimeText.visibility = View.GONE
                        secondTimeHour = null
                        secondTimeMinute = null
                        binding.secondTimeText.text = ""

                        binding.setThirdButton.visibility = View.GONE
                        binding.thirdTimeText.visibility = View.GONE
                        thirdTimeHour = null
                        thirdTimeMinute = null
                        binding.thirdTimeText.text = ""

                        binding.setFourthButton.visibility = View.GONE
                        binding.fourthTimeText.visibility = View.GONE
                        fourthTimeHour = null
                        fourthTimeMinute = null
                        binding.fourthTimeText.text = ""

                        binding.setFifthButton.visibility = View.GONE
                        binding.fifthTimeText.visibility = View.GONE
                        fifthTimeHour = null
                        fifthTimeMinute = null
                        binding.fifthTimeText.text = ""
                    }
                    1 -> {
                        spinnerPosition = 1

                        binding.setFirstButton.visibility = View.VISIBLE
                        binding.firstTimeText.visibility = View.VISIBLE

                        binding.setSecondButton.visibility = View.VISIBLE
                        binding.secondTimeText.visibility = View.VISIBLE

                        binding.setThirdButton.visibility = View.GONE
                        binding.thirdTimeText.visibility = View.GONE
                        binding.thirdTimeText.text = ""
                        thirdTimeHour = null
                        thirdTimeMinute = null

                        binding.setFourthButton.visibility = View.GONE
                        binding.fourthTimeText.visibility = View.GONE
                        fourthTimeHour = null
                        fourthTimeMinute = null
                        binding.fourthTimeText.text = ""

                        binding.setFifthButton.visibility = View.GONE
                        binding.fifthTimeText.visibility = View.GONE
                        fifthTimeHour = null
                        fifthTimeMinute = null
                        binding.fifthTimeText.text = ""
                    }
                    2 -> {
                        spinnerPosition = 2

                        binding.setFirstButton.visibility = View.VISIBLE
                        binding.firstTimeText.visibility = View.VISIBLE

                        binding.setSecondButton.visibility = View.VISIBLE
                        binding.secondTimeText.visibility = View.VISIBLE

                        binding.setThirdButton.visibility = View.VISIBLE
                        binding.thirdTimeText.visibility = View.VISIBLE

                        binding.setFourthButton.visibility = View.GONE
                        binding.fourthTimeText.visibility = View.GONE
                        fourthTimeHour = null
                        fourthTimeMinute = null
                        binding.fourthTimeText.text = ""

                        binding.setFifthButton.visibility = View.GONE
                        binding.fifthTimeText.visibility = View.GONE
                        fifthTimeHour = null
                        fifthTimeMinute = null
                        binding.fifthTimeText.text = ""
                    }
                    3 -> {
                        spinnerPosition = 3

                        binding.setFirstButton.visibility = View.VISIBLE
                        binding.firstTimeText.visibility = View.VISIBLE

                        binding.setSecondButton.visibility = View.VISIBLE
                        binding.secondTimeText.visibility = View.VISIBLE

                        binding.setThirdButton.visibility = View.VISIBLE
                        binding.thirdTimeText.visibility = View.VISIBLE

                        binding.setFourthButton.visibility = View.VISIBLE
                        binding.fourthTimeText.visibility = View.VISIBLE

                        binding.setFifthButton.visibility = View.GONE
                        binding.fifthTimeText.visibility = View.GONE
                        fifthTimeHour = null
                        fifthTimeMinute = null
                        binding.fifthTimeText.text = ""
                    }
                    4 -> {
                        spinnerPosition = 4

                        binding.setFirstButton.visibility = View.VISIBLE
                        binding.firstTimeText.visibility = View.VISIBLE

                        binding.setSecondButton.visibility = View.VISIBLE
                        binding.secondTimeText.visibility = View.VISIBLE

                        binding.setThirdButton.visibility = View.VISIBLE
                        binding.thirdTimeText.visibility = View.VISIBLE

                        binding.setFourthButton.visibility = View.VISIBLE
                        binding.fourthTimeText.visibility = View.VISIBLE

                        binding.setFifthButton.visibility = View.VISIBLE
                        binding.fifthTimeText.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setTime(){
        setFirstTime()
        setSecondTime()
        setThirdTime()
        setFourthTime()
        setFifthTime()
    }

    @SuppressLint("SetTextI18n")
    private fun setFirstTime(){
        binding.setFirstButton.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                firstTimeHour = hourOfDay
                firstTimeMinute = minute

                setCalendar(hourOfDay, minute)

                timeInMillisArray[0] = calendar.timeInMillis

                val firstTimeHourString = if(firstTimeHour!!<10) "0${firstTimeHour}" else "$firstTimeHour"
                val firstTimeMinuteString = if(firstTimeMinute!!<10) "0${firstTimeMinute}" else "$firstTimeMinute"

                binding.firstTimeText.text = "${firstTimeHourString}:${firstTimeMinuteString}"
            }

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                timeSetListener,
                12,
                0,
                true
            )
            timePickerDialog.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSecondTime(){
        binding.setSecondButton.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                secondTimeHour = hourOfDay
                secondTimeMinute = minute

                setCalendar(hourOfDay, minute)

                timeInMillisArray[1] = calendar.timeInMillis

                val secondTimeHourString = if(secondTimeHour!!<10) "0${secondTimeHour}" else "$secondTimeHour"
                val secondTimeMinuteString = if(secondTimeMinute!!<10) "0${secondTimeMinute}" else "$secondTimeMinute"

                binding.secondTimeText.text = "${secondTimeHourString}:${secondTimeMinuteString}"
            }

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                timeSetListener,
                12,
                0,
                true
            )
            timePickerDialog.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setThirdTime(){
        binding.setThirdButton.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                thirdTimeHour = hourOfDay
                thirdTimeMinute = minute

                setCalendar(hourOfDay, minute)

                timeInMillisArray[2] = calendar.timeInMillis

                val thirdTimeHourString = if(thirdTimeHour!!<10) "0${thirdTimeHour}" else "$thirdTimeHour"
                val thirdTimeMinuteString = if(thirdTimeMinute!!<10) "0${thirdTimeMinute}" else "$thirdTimeMinute"

                binding.thirdTimeText.text = "${thirdTimeHourString}:${thirdTimeMinuteString}"
            }

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                timeSetListener,
                12,
                0,
                true
            )
            timePickerDialog.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setFourthTime(){
        binding.setFourthButton.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                fourthTimeHour = hourOfDay
                fourthTimeMinute = minute

                setCalendar(hourOfDay, minute)

                timeInMillisArray[3] = calendar.timeInMillis

                val fourthTimeHourString = if(fourthTimeHour!!<10) "0${fourthTimeHour}" else "$fourthTimeHour"
                val fourthTimeMinuteString = if(fourthTimeMinute!!<10) "0${fourthTimeMinute}" else "$fourthTimeMinute"

                binding.fourthTimeText.text = "${fourthTimeHourString}:${fourthTimeMinuteString}"
            }

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                timeSetListener,
                12,
                0,
                true
            )
            timePickerDialog.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setFifthTime(){
        binding.setFifthButton.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                fifthTimeHour = hourOfDay
                fifthTimeMinute = minute

                setCalendar(hourOfDay, minute)

                timeInMillisArray[4] = calendar.timeInMillis

                val fifthTimeHourString = if(fifthTimeHour!!<10) "0${fifthTimeHour}" else "$fifthTimeHour"
                val fifthTimeMinuteString = if(fifthTimeMinute!!<10) "0${fifthTimeMinute}" else "$fifthTimeMinute"

                binding.fifthTimeText.text = "${fifthTimeHourString}:${fifthTimeMinuteString}"
            }

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                timeSetListener,
                12,
                0,
                true
            )
            timePickerDialog.show()
        }
    }

    private fun setCalendar(hourOfDay: Int, minute: Int){
        if(hourOfDay< LocalDateTime.now().hour){
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay+24)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }
        else if(hourOfDay== LocalDateTime.now().hour && minute< LocalDateTime.now().minute){
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay+24)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }
        else{
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }
    }

    private fun getPillName(): String = binding.pillNameText.text.toString()

    private fun setPill(){
        binding.setButton.setOnClickListener {
            val pillName = getPillName()
            if(spinnerNullController() || pillName.isEmpty()) {
                Toast.makeText(requireContext(), "Bilgileri doldurunuz", Toast.LENGTH_LONG).show()
            }
            else{
                val pillId: Int? = viewModel.insertPill(Pill(pillName = getPillName()))
                if(pillId!=null){
                    setAlarm(pillName, pillId)
                }
            }
        }
    }

    private fun spinnerNullController(): Boolean{
        return when(spinnerPosition){
            0 -> {
                isTimeNull(firstTimeHour, firstTimeMinute, 0)
            }
            1 -> {
                isTimeNull(firstTimeHour, firstTimeMinute, 0)
                        || isTimeNull(secondTimeHour, secondTimeMinute, 1)
            }
            2 -> {
                isTimeNull(firstTimeHour, firstTimeMinute, 0)
                        || isTimeNull(secondTimeHour, secondTimeMinute, 1)
                        || isTimeNull(thirdTimeHour, thirdTimeMinute, 2)
            }
            3 -> {
                isTimeNull(firstTimeHour, firstTimeMinute, 0)
                        || isTimeNull(secondTimeHour, secondTimeMinute, 1)
                        || isTimeNull(thirdTimeHour, thirdTimeMinute, 2)
                        || isTimeNull(fourthTimeHour, fourthTimeMinute, 3)
            }
            4 -> {
                isTimeNull(firstTimeHour, firstTimeMinute, 0)
                        || isTimeNull(secondTimeHour, secondTimeMinute, 1)
                        || isTimeNull(thirdTimeHour, thirdTimeMinute, 2)
                        || isTimeNull(fourthTimeHour, fourthTimeMinute, 3)
                        || isTimeNull(fifthTimeHour, fifthTimeMinute, 4)
            }
            else -> {
                false
            }
        }
    }

    private fun isTimeNull(hour: Int?, minute: Int?, th: Int): Boolean{
        if(hour == null || minute == null) {
            Toast.makeText(requireContext(), "${th+1}. ilaç saatini seçiniz", Toast.LENGTH_LONG).show()
            return true
        }
        else{
            return false
        }
    }

    @SuppressLint("ScheduleExactAlarm", "ShortAlarm")
    private fun setAlarm(pillName: String, pillId: Int){
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        Toast.makeText(requireContext(), "Alarm Ayarlandı", Toast.LENGTH_SHORT).show()

        for(th in 0..spinnerPosition){
            if(timeInMillisArray[th]==null){
                break
            }
            else{
                createNotificationChannel(pillName)
                val alarmId = viewModel.insertPillAlarm(PillAlarm(pillId = pillId.toLong(), alarmTime = timeInMillisArray[th]!!))
                if(alarmId!=null){
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillisArray[th]!!, AlarmManager.INTERVAL_DAY, setPendingIntentForAlarmManager(pillName, alarmId, th))
                    //alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillisArray[th]!!, setPendingIntentForAlarmManager(pillName, alarmId, th))
                }
            }
        }

    }

    private fun setPendingIntentForAlarmManager(pillName: String, alarmId: Int, th:Int) =
        PendingIntent.getBroadcast(requireContext(), alarmId, setIntentForPendingIntent(pillName, alarmId, th), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    private fun setIntentForPendingIntent(pillName: String, alarmId: Int, th:Int): Intent {
        return Intent(requireContext(), AlarmReceiver::class.java).apply {
            putExtra("header", pillName)
            putExtra("comment", "${th + 1}. İlaç Vakti!!")
            putExtra("id", alarmId)
        }
    }

    private fun createNotificationChannel(header: String) {
        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("MEDICATION_REMINDER_CHANNEL", header, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = header
        notificationManager.createNotificationChannel(channel)
    }

    private fun observeReadyToGoPillFragment(){
        viewModel.readyToGoPillFragmentB.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it==true) toPillFragment()
        })
    }

    private fun toPillFragment(){
        Navigation.findNavController(requireView()).navigate(PillDetailsFragmentDirections.actionPillDetailsFragmentToCategoriesFragment())
    }

}