<template>
    <div class="ViewInstructor" id="mainContainer">
        <h1>View instructors</h1>
        <br>
        
        <VBox id="verticalContainer">
            <input id="inputBox" type="text" v-model='search' placeholder="Search by name"></input>

            <table id="instructorTable">
                <thead>
                    <tr>
                        <th width="20%">Profile pic</th>
                        <th width="16%">Name</th>
                        <th width="16%">Speciality</th>
                        <th width="48%">Description</th>
                    </tr>
                </thead>
                <tbody>

                    <template v-if="instructors.length === 0">
                        <tr>
                            <td colspan="4">No instructor</td>
                        </tr>
                    </template>

                    <template v-else>
                        <tr v-for="(instructor, index) in filteredInstructors" :key="index">
                            <td>{{ instructor.picture }}</td>
                            <td >{{ instructor.name }}</td>
                            <td>{{ instructor.speciality }}</td>
                            <td>{{ instructor.description }}</td>
                        </tr>
                    </template>
                    <!--<tr>
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
                -->
                </tbody>
            </table>
        </VBox>
    </div>
</template>

<script>
  
import axios from 'axios'
import config from '../../../config'

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
                filteredInstructors: [],
                search: '',
            };
        },

        mounted() {
            this.getInstructors();
        },

        methods: {
            getInstructors() {
                AXIOS.get('/instructors')
                    .then(response => {
                        this.instructors = response.data;

                        this.instructorsTable= response.data.map(instructor => (
                            {
                                picture: instructor.picture,
                                name: instructor.name,
                                speciality: instructor.speciality,
                                description: instructor.description
                            }));
                            })
                            .catch(e => {
                            console.error('Error fetching instructors', e);
                    });
            },
        },
        computed: {
        
            filteredInstructors: function() {
                const query = this.search.toLowerCase();
                return this.instructors.filter(instructor => 
                    instructor.name.toLowerCase().includes(query)
                );
            },
        }


    }
</script>

<style scoped src="../../assets/main.css"></style>