<template>
<div class="ViewActivityTable" id="mainContainer">
  <h1>Future classes</h1>
  <br>
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
            <td colspan="4">No activities</td>
          </tr>
        </template>
        <template v-else>
          <tr v-for="scheduledActivity in scheduledActivities" :key="scheduledActivity" @click="showActivityDetails(scheduledActivity)">
            <td>{{ scheduledActivity.activity.name }}</td>
            <td>{{ scheduledActivity.activity.subCategory }}</td>
            <td>{{ scheduledActivity.date }}</td>
            <td>{{ scheduledActivity.capacity }}</td>
          </tr>
        </template>
      </tbody>
    </table>
    
    <ViewActivity v-if="selectedActivity" :activity="selectedActivity" @close="closePopup" style="align-self: center;"/>
    
    <br>
  </div>
</template>

<script>

import { ref } from 'vue'
import axios from 'axios'
import config from '../../../config'
import ViewActivity from './ViewActivity.vue';
let input = ref("");

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
    };
  },


  async created() {
    // Make HTTP request to fetch scheduled activities from backend
    try {
      const response = await AXIOS.get('/scheduledActivities');
      this.scheduledActivities = response.data;
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
    filteredActivities() {
      return this.scheduledActivities.filter(activity => {
        return activity.name.toLowerCase().includes(this.input.toLowerCase());
      });
    }
  },

  components: {
    ViewActivity
  },
}
function filteredList() {
  return this.scheduledActivities.filter((activity) => {
    activity.name.toLowerCase().includes(this.input.toLowerCase());
  });
};
</script>

<style src="../../assets/main.css"></style>