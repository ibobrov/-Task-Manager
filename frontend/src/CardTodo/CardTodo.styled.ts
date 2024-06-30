import styled from "styled-components";

const CardTodoContainer = styled.li`
    border: 1px, solid, rgba(61, 71, 81, 0.3);
    background-color: rgba(29, 33, 38, 0.1);
    padding: 24px;
    display: flex;
    justify-content: space-between; 
`

const Button = styled.button`
    background-color: rgba(144, 202, 249, 0.08);
    border: 1px solid rgb(144, 202, 249);
`

export { CardTodoContainer, Button }