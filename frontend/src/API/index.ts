import axios from "axios";
import { GetAllResponse, TaskType } from "./types";


const baseURL = "http://127.0.0.1:8080/api/public";
const instance = axios.create({
        baseURL,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json'
        },

    });

export const API = {
    
    async getAll(): Promise<GetAllResponse> {
        const res = await instance.get('/task')
        return res.data
    },
    async putTask(id: string, task: TaskType) {
        const res = await instance.put(`/task/${id}`, task)
        return res.data
    },
    async deleteTask(id: string) {
        const res = await instance.delete(`/task/${id}`)
        return res.data
    }
}