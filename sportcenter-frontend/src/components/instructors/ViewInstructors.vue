<template>
    <div class="ViewInstructor" id="mainContainer">
        <h1>Our instructors</h1>
        <br>
        
        <VBox id="verticalContainer">
            <input id="inputBox" type="text" v-model='search' placeholder="Search by name"></input>

            <table id="instructorTable">
                <thead>
                    <tr>
                        <th width="16%">Name</th>
                        <th width="48%">Description</th>
                    </tr>
                </thead>
                <tbody>

                    <template v-if="instructors.length === 0">
                        <tr>
                            <td colspan="3">No instructor</td>
                        </tr>
                    </template>

                    <template v-else>
                        <tr v-for="(instructor, index) in instructors" :key="index">
                            <td>{{ instructor.account.username }}</td>
                            <td>{{ instructor.description }}</td>
                        </tr>
                    </template>
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
                search: '',
            };
        },
        async created() {
            try {
                const response = await AXIOS.get('/getAllInstructorsByStatus/Active');
                this.instructors = response.data;
            } catch (error) {
                console.error('Error fetching instructors', error.message);
            }
        },
    }
</script>

<style scoped src="../../assets/main.css"></style>