<script setup>

</script>

<template>
    <div>
        <h1>헤더입니다.</h1>
        <nav>
        <h1>nav 입니다.</h1>
        <router-link :to="{name : 'home'}">home</router-link>
        <router-link :to="{name : 'login'}">login</router-link>
        <router-link :to="{name : 'board'}">board</router-link>
    </nav>
    </div>

</template>

<style scoped>

</style>