<template>
    <header :class = "{'scrolled-nav' : scrolledPosition} ">
        <nav>
            <div class="branding">
                <img src="@/assets/SC.png" alt=""/>
            </div>
            <ul v-show="!mobile" class="nav-links">
                <li><router-link class="link" :to="{name: 'Home'}">Home</router-link></li>
                <li><router-link class="link" :to="{name: ''}">About</router-link></li>
                <li><router-link class="link" :to="{name: ''}">Services</router-link></li>
                <li><router-link class="link" :to="{name: ''}">Contact</router-link></li>
            </ul>
            <div class="icon">
                <i @click ="toggleMobileNav" v-show="mobile" class="fas fa-bars" :class="{'icon-active' : mobileNav}"></i>
            </div>
            <Transition name="mobile-nav"></Transition>
            <ul v-show="mobileNav" class="dropdown-nav">
                <li><router-link class="link" :to="{name: 'Home'}">Home</router-link></li>
                <li><router-link class="link" :to="{name: ''}">About</router-link></li>
                <li><router-link class="link" :to="{name: ''}">Services</router-link></li>
                <li><router-link class="link" :to="{name: ''}">Contact</router-link></li>
            </ul>
        </nav>
    </header>
</template>

<script>
export default {
    data() {
        return {
            scrolledPosition: false
        }
    },
    mounted() {
        window.addEventListener('scroll', this.handleScroll)
    },
    methods: {
        handleScroll() {
            if (window.scrollY > 50) {
                this.scrolledPosition = true
            } else {
                this.scrolledPosition = false
            }
        }
    }
}
</script>

<style lang="scss" scoped>

</style>