import {TaskStatus} from "../CardTodo/types";

type GetAllResponse = TaskType[]

type TaskType = {
    key: number,
    val: string
    status?: TaskStatus
}

export type { GetAllResponse, TaskType}