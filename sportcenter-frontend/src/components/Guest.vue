<template>
    <div style="width: 1200px;">
        <div class="ViewActivityTable" id="mainContainer">
        <h1>Future classes</h1>
        <br>
            
        <!--input id="inputBox" type="text" v-model="search" placeholder="Search activities"-->


            <table id="activityTable" align="center" width="900px">
            <thead>
                <tr>
                <th width="25%">Name</th>
                <th width="25%">Category</th>
                <th width="25%">Date</th>
                <th width="25%">Capacity</th>
                </tr>
            </thead>
            <tbody>
                <template v-if="scheduledActivities.length === 0">
                <tr>
                    <td colspan="4">No Scheduled Activities</td>
                </tr>
                </template>

                <template v-else>
                <!--tr v-for="(activity, index) in filteredActivities" :key="index" @click="showActivityDetails(activity)"-->
                <tr v-for="(scheduledActivity, index) in scheduledActivities" :key="index" @click="showActivityDetails(scheduledActivity)">
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
            <div class="button-container">
            </div>
        </div>

        <div id="mainContainer">
            <h1>Our instructors</h1>
            <VBox id="verticalContainer">
                <!--input id="inputBox" type="username" placeholder="Search by name"></input-->

                <table id="instructorTable">
                <thead>
                    <tr>
                        <th width="20%">Profile pic</th>
                        <th width="16%">Name</th>
                        <th width="48%">Description</th>
                    </tr>
                </thead>
                <tbody>

                    <template v-if="instructors.length === 0">
                        <tr>
                            <td colspan="3">No Instructor</td>
                        </tr>
                    </template>

                    <template v-else>
                        <tr v-for="(instructor, index) in instructors" :key="index">
                            <td>{{ instructor.profilePicURL }}</td>
                            <td>{{ instructor.account.username }}</td>
                            <td>{{ instructor.description }}</td>
                        </tr>
                    </template>
                        <!--tr>
                            <td><img id="profilePic" src="@/assets/zyzz.jpg" alt="Instructor" width="140" height="140"></td>
                            <td id="information">Zyzz Ouh</td>
                            <td>YEET</td>
                            <td>Expert in goat yoga, certified in pilates and zumba, all the moms love me, I'm the best instructor in the world (I started a cult)</td>
                        </tr>

                        <tr>
                            <td><img id="profilePic" src="@/assets/ronnie.jpeg" alt="Instructor" width="140" height="140"></td>
                            <td id="information">Rouni Coldmam</td>
                            <td>YEET</td>
                            <td>Expert in powerlifting, certified in squats and bench press, all the guys love him, the sexiest guy in the world (according to his girlfriend)</td>
                        </tr>

                        <tr>
                            <td><img id="profilePic" src="@/assets/john.jpg" alt="Instructor" width="140" height="140"></td>
                            <td id="information">Jon Sepa</td>
                            <td>YEET</td>
                            <td>Expert in meditation, certified in memory loss and memes, never seen at the gym tbf, I don't even know if he's still active</td>
                        </tr-->
                    </tbody>
                </table>
            </VBox>
        </div>
    </div>
</template>

<script>
import Vue from 'vue'
import axios from 'axios'
import config from '../../config'
import ViewActivity from './activity/ViewActivity.vue';

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'Guest',
  data() {
    return {
        accountType: 'Guest',
        instructors: [],
        scheduledActivities: [],
        filteredActivityData: [],
        selectedActivity: null,
        search:'',

      //logic here would get all activities

      // scheduledActivities: [
      //   { name: 'Borneo', category: 'Expedition', date: '6 march', capacity: 30 },
      //   { name: 'Trifecta', category: 'YoloSwag', date: '6 april', capacity: 10 },
      //   { name: 'Running', category: 'Cardio', date: '6 january', capacity: 20 },
      //   { name: 'INSTAGATION', category: 'Vroom', date: '6 january', capacity: 20 },
      // ],
    };
  },


  


  async created() {
    if (this.getAccountType() === 'Owner' || this.getAccountType() === 'Instructor') {
      try {
        const response = await AXIOS.get('/getAllInstructors');
        this.instructors = response.data;
      } catch (error) {
        console.error('Error fetching instructors:', error);
      }
      try {
        const response = await AXIOS.get('/scheduledActivities');
        this.scheduledActivities = response.data;
      } catch (error) {
        console.error('Error fetching scheduled activities:', error);
      }
    }
    else {
      try {
        const response = await AXIOS.get('/getAllInstructorsByStatus/Active');
        this.instructors = response.data;
      } catch (error) {
        console.error('Error fetching instructors:', error);
      }
      try {
          const response = await AXIOS.get('/scheduledActivities');
          this.scheduledActivities = response.data;
        } catch (error) {
          console.error('Error fetching scheduled activities:', error);
        }

    }
    try {
        const response = await AXIOS.post('/createOwner');
        console.log("Owner should be created " + response.data);
      } catch (error) {
        console.log('Error creating owner', error.message);
      }

    
  },

  methods: {
    goLogin() {
      this.$router.push('/app/auth/login');
    },

    goCreateAccount() {
      this.$router.push('/app/auth/createaccount');
    },

    showActivityDetails(activity) {
      this.selectedActivity = activity;
    },
    closePopup() {
      this.selectedActivity = null;
    },

    getAccountType() {
      localStorage.setItem('accountType', Math.random() < 0.5 ? "Customer" : "Instructor_1");
      return localStorage.getItem('accountType')
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
  components: {
    ViewActivity
  }
};
</script>

<style src="@/assets/main.css"></style>