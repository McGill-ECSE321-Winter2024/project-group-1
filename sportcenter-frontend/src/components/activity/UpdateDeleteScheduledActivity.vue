<template>
  <div style="width: 1200px;">
    <div class="UpdateDeleteScheduledActivity" id="mainContainer" style="margin-bottom: 0px;">
      <h1>Scheduled activities management</h1>
      <br>
      
      <input id="inputBox" type="text" v-model="search" placeholder="Search a scheduled activity">

      <table id="activityTable" align="center" width="700">
        <thead>
          <tr>
            <th width="100">Name</th>
            <th width="100">Category</th>
            <th width="100">Date</th>
            <th width="100">Capacity</th>
          </tr>
        </thead>
        <tbody>
          <template v-if="scheduledActivities.length === 0">
            <tr>
              <td colspan="4">No Scheduled Activities</td>
            </tr>
          </template>

          <template v-else>
            <tr v-for="(activity, index) in filteredActivities" :key="index">
              <td>{{ activity.activity.name }}</td>
              <td>{{ activity.activity.category }}</td>
              <td>{{ activity.date }}</td>
              <td>{{ activity.capacity }}</td>
            </tr>
          </template>

        </tbody>
      </table>
      
      <ViewActivity v-if="selectedActivity" :activity="selectedActivity" @close="closePopup" style="align-self: center;"/>
      
      <br>
    </div>

    <div id="horizontalContainer">
      <div id="mainContainer" style="width: 630px;">
        <VBox id="verticalContainer">
            <h1>Update schedule</h1>
            <input id="inputBox" placeholder="Name of activity to update">
            <HBox id="horizontalContainer">
                <input id="datePickerInput" type="date" name="scheduled">
                <h2 style="align-self:center;">from</h2>
                <input id="datePickerInput" type="time" name="startTime">
                <h2 style="align-self:center;">to</h2>
                <input id="datePickerInput" type="time" name="endTime">
            </HBox>
            <button id="mainButton">Update</button>
        </VBox>
      </div>

      <div id="mainContainer" style="width: 460px; margin-left: 80px;">
        <h1>Delete schedule</h1>
        <input id="inputBox" placeholder="Name of scheduled activity to delete">
        <input id="inputBox" placeholder="Confirm name of activity to delete">
        <input id="inputBox" placeholder="Write 'DELETE' to confirm deletion">
        <button id="destroyButton">Delete</button>
      </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios'
import config from '../../../config'
import ViewActivity from './ViewActivity.vue';

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  data() {
    return {
      
      scheduledActivities: [],
      filteredActivityData: [],
      selectedActivity: null,
      search:'',



      //logic here would get all activities

    };
  },


  async created() {
      // Make HTTP request to fetch scheduled activities from backend
     
     try {

      const response = await AXIOS.get('/scheduledActivities')
      this.scheduledActivities = response.data
      }
      catch (error) {

        console.error('Error fetching scheduled activities:', error);
      }
        
    },


  



  methods: {

 
    showActivityDetails(activity) {
      this.selectedActivity = activity;
    },
    closePopup() {
      this.selectedActivity = null;
    },
        getAccountType() {
      return localStorage.getItem('accountType');
    },
    setAccountType(accountType) {
      localStorage.setItem('accountType', accountType);
    },
    getUsername() {
      return localStorage.getItem('username');
    },
    setUsername(username) {
      localStorage.setItem('username', username);
    },
    getLoggedIn() {
      return localStorage.getItem('loggedIn') === 'true';
    },
    setLoggedIn(loggedIn) {
      localStorage.setItem('loggedIn', loggedIn);
    },
    getTime() {
      return localStorage.getItem('time');
    },
    setTime(time) {
      localStorage.setItem('time', time);
    },
    getDebuggingMode() {
      return localStorage.getItem('debugging_mode') === 'true';
    },
    setDebuggingMode(debugging_mode) {
      localStorage.setItem('debugging_mode', debugging_mode);
    },
    getLanguage() {
      return localStorage.getItem('language');
    },
    setLanguage(language) {
      localStorage.setItem('language', language);
    },
    getDarkMode() {
      return localStorage.getItem('dark_mode') === 'true';
    },
    setDarkMode(dark_mode) {
      localStorage.setItem('dark_mode', dark_mode);
    },


  }, //end of methods

  computed: {
  // Filter activities based on search query
  filteredActivities: function() {
    const query = this.search.toLowerCase();
    return this.scheduledActivities.filter(activity =>
      activity.name.toLowerCase().includes(query) ||
      activity.category.toLowerCase().includes(query) ||
      activity.date.toLowerCase().includes(query) ||
      activity.capacity.toString().includes(query)
    );
  },
  },
  components: {
    ViewActivity
  }
};
</script>

<style src="../../assets/main.css"></style>