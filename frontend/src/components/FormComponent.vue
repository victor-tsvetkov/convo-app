<script setup>
    import {reactive} from 'vue';

    const props = defineProps({
        data: Object
    });
    const {data} = props;
    // do not use same name with ref
    const formData = reactive(data);

</script>

<template>
    <el-form :model="formData" label-width="auto" style="max-width: 600px">
        <el-form-item v-for="item in formData" :label="item.label">
            <el-input v-model="item.value" />
        </el-form-item>
    </el-form>
</template>

<style scoped>

</style>