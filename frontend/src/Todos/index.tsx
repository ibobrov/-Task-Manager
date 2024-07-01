import React, { useEffect, useState } from "react";
import { CardTodo } from "../CardTodo";
import { TaskType } from "../API/types";
import { API } from "../API";

const Todos = () => {
    const [todoList, setTodoList] = useState<TaskType[]>([]);

    useEffect(() => {
        (async () => {
            setTodoList(await API.getAll())
        })();
    }, []);

    return (
        <>
            <h3>Список задач</h3>
            <ul>
                {todoList?.map((item) => <CardTodo key={item.key} setTodoList={setTodoList} id={item.key} text={item.val} status={item.status} />)}
            </ul>
        </>
    );
};

export { Todos };