import {TaskType} from "../API/types";
import React from "react";

interface CardTodoProps {
    setTodoList: React.Dispatch<React.SetStateAction<TaskType[]>>
    id: number
    text: string
    status?: TaskStatus
}

export enum TaskStatus {
    OPENED,
    CLOSED
}

export type { CardTodoProps };