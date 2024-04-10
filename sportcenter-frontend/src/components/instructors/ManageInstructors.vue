<template>
    <div class="ManageInstructor" id="mainContainer">
        <h1>Manage instructors</h1>
        <br>

        <HBox id="horizontalContainer"> 
            <input id="inputBox" type="text" v-model="search" placeholder="Search by name"></input>
            <HBox id="horizontalContainer">
                <button id="mainButton" @click="approveInstructor()">Approve Instructor</button>
                <button id="mainButton" @click="dissaproveInstructor()">Disapprove Instructor</button>
                <button id="mainButton" @click="deleteInstructor()">Delete Instructor</button>
            </HBox>
        </HBox>

        <VBox id="verticalContainer">
            <table id="manageInstructorTable">
                <thead>
                    <tr>
                        <th width="12%">Profile pic</th>
                        <th width="16%">Name</th>
                        <th width="46%">Description</th>
                        <th width="10%">Status</th>
                        <th width="10%">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <template v-if="instructors.length === 0"> 
                        <tr>
                            <td colspan="5">No instructor</td>
                        </tr> 
                    </template> 
                    <template v-else>
                        <tr v-for="(instructor, index) in instructors" :key="index">
                            <!--td><img id="profilePic" src="require(`../assets/${instructor.profilePicURL}`)" alt="Instructor" width="120" height="120"></td-->
                            <td>{{ instructor.profilePicURL }}</td>
                            <td>{{ instructor.account.username }}</td>
                            <td>{{ instructor.description }}</td>
                            <td>{{ instructor.status }}</td>
                            <td>
                                <VBox id="verticalContainer" v-if="instructor.status=='active'">
                                    <button id="subButton">deactivate</button>
                                </VBox> 
                                <VBox id="verticalContainer" v-if="instructor.status=='pending'">
                                    <button id="subButton">Approve</button>
                                    <button id="subButton">Disapprove</button>
                                </VBox> 
                                <VBox id="verticalContainer" v-if="instructor.status=='inactive'">
                                    <button id="subButton">activate</button>
                                </VBox> 
                            </td>
                        </tr>
                    </template>
                    <!--<tr>
                        <td><img id="profilePic" src="@/assets/zyzz.jpg" alt="Instructor" width="120" height="120"></td>
                        <td id="information">Zyzz Ouh</td>
                        <td>Bodybuilding</td>
                        <td>Expert in goat yoga, certified in pilates and zumba, all the moms love me, I'm the best instructor in the world (I started a cult)</td>
                        <td>
                            <VBox id="verticalContainer" v-if="$accountUsername">
                                <button id="subButton">Approve</button>
                                <button id="subButton">Disapprove</button>
                                <button id="subButton">Active/Inactive</button>
                            </VBox>
                        </td>
                    </tr>

                    <tr>
                        <td><img id="profilePic" src="@/assets/ronnie.jpeg" alt="Instructor" width="120" height="120"></td>
                        <td id="information">Rouni Coldmam</td>
                        <td>Strength</td>
                        <td>Expert in powerlifting, certified in squats and bench press, all the guys love him, the sexiest guy in the world (according to his girlfriend)</td>
                        <td>
                            <VBox id="verticalContainer">
                                <button id="subButton">Approve</button>
                                <button id="subButton">Disapprove</button>
                                <button id="subButton">Active/Inactive</button>
                            </VBox>
                        </td>
                    </tr>

                    <tr>
                        <td><img id="profilePic" src="@/assets/john.jpg" alt="Instructor" width="120" height="120"></td>
                        <td id="information">Jon Sepa</td>
                        <td>Cardio</td>
                        <td>Expert in meditation, certified in memory loss and memes, never seen at the gym tbf, I don't even know if he's still active</td>
                        <td>
                            <VBox id="verticalContainer">
                                <button id="subButton">Approve</button>
                                <button id="subButton">Disapprove</button>
                                <button id="subButton">Active/Inactive</button>
                            </VBox>
                        </td>
                    </tr>

                    <tr>
                        <td><img id="profilePic" src="@/assets/messi.jpg" alt="Instructor" width="120" height="120"></td>
                        <td id="information">The Little Guy from Rizzario</td>
                        <td>GOAT</td>
                        <td>Expert in football, World Cup winner, MVP, fuck Ronaldo</td>
                        <td>
                            <VBox id="verticalContainer">
                                <button id="subButton">Approve</button>
                                <button id="subButton">Disapprove</button>
                                <button id="subButton">Active/Inactive</button>
                            </VBox>
                        </td>
                    </tr>-->
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
            search:'',
        };
    },
    async created(){
        try{
            const response = await AXIOS.get('/getAllInstructors')
            this.instructors = response.data
        } catch (error){
            console.log('Error fetching instructors', error.message);
        }
    },
    methods:
    {
        async approveInstructor(){
            try{
                const response = await AXIOS.put('/approveInstructor/' + this.search)
                console.log(response.data)
                alert('Instructor approved')
                this.clearInputs();
                this.created();
                this.$router.go();
            } catch (error){
                console.log('Error approving instructor', error.message);
            }
        },
        async dissaproveInstructor(){
            try{
                const response = await AXIOS.put('/disapproveInstructor/' + this.search)
                console.log(response.data)
                alert('Instructor disapproved')
                this.clearInputs();
                this.created();
                this.$router.go();
            } catch (error){
                console.log('Error disapproving instructor', error.message);
            }
        },
        async deleteInstructor(){
            try{
                const response = await AXIOS.delete('/deleteInstructor')
                console.log(response.data)
                alert('Instructor deleted')
                this.clearInputs();
                this.created();
                this.$router.go();
            } catch (error){
                console.log('Error deleting instructor', error.message);
            }
        },
        async clearInputs(){
            this.search = null;
        }
    },

}
</script>

<style scoped src="../../assets/main.css"></style>