<script setup>
    import {reactive} from 'vue';

    const props = defineProps({
        data: Object
    });
    const {data} = props;
    const formData = reactive(data.data);

    const onSubmit = () => {
        if (!!data.submitHandler) {
            data.submitHandler();
        }
    }

</script>

<template>
    <el-form  label-width="auto" style="max-width: 600px">
        <el-form-item v-for="item in formData" :key="item.id" :label="item.label">
            <el-input v-if="item.type === 'text'" :type="item.type" v-model="item.value"></el-input>
            <el-input v-if="item.type === 'password'" :type="item.type" v-model="item.value"></el-input>
            <el-select v-if="item.type === 'select'" v-model="item.value"
                       clearable
                       :placeholder="item.placeholder">
                <el-option
                    v-for="option in item.options"
                    :key="option.id"
                    :label="option.label"
                    :value="option.value"
                ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">{{data.buttonName}}</el-button>
            <el-button>Отмена</el-button>
        </el-form-item>
    </el-form>
</template>

<style scoped>

</style>