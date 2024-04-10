<template>
    <div>
        <div class="ViewActivityTable" id="mainContainer">
        <h1>View activities</h1>
        <br>
            
        <input id="inputBox" type="text" v-model="search" placeholder="Search activities">

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
                <tr v-for="(activity, index) in filteredActivities" :key="index" @click="showActivityDetails(activity)">
                    <td>{{ activity.name }}</td>
                    <td>{{ activity.category }}</td>
                    <td>{{ activity.date }}</td>
                    <td>{{ activity.capacity }}</td>
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
            <h1>View instructors</h1>
            <VBox id="verticalContainer">
                <input id="inputBox" type="username" placeholder="Search by name"></input>

                <table id="table">
                    <thead>
                        <tr>
                            <th width="20%">Profile pic</th>
                            <th width="16%">Name</th>
                            <th width="16%">Speciality</th>
                            <th width="48%">Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- <template v-if="instructors.length === 0"> -->
                            <!-- <tr>
                                <td colspan="4">No instructor</td>
                            </tr> -->
                        <!-- </template> -->
                        <tr>
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
                        </tr>
                    </tbody>
                </table>
            </VBox>
        </div>
    </div>
</template>

<script>

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
  data() {
    return {
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


  mounted() {
    // Call method to fetch scheduled activities when the component is mounted
    this.fetchScheduledActivities();
  },



  



  methods: {

    fetchScheduledActivities() {
      // Make HTTP request to fetch scheduled activities from backend
      axios.get('/scheduledActivities')
        .then(response => {
          // Assign response data to scheduledActivities
          this.scheduledActivities = response.data;

          this.scheduledActivitiesTable = response.data.map(activity => ({
          activityName: activity.activity.name,
          activityCategory: activity.activity.category,
          date: activity.date,
          capacity: activity.capacity
        }));
        })
        .catch(error => {
          console.error('Error fetching scheduled activities:', error);
        });
    },

 
    showActivityDetails(activity) {
      this.selectedActivity = activity;
    },
    closePopup() {
      this.selectedActivity = null;
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

<style src="@/assets/main.css"></style>