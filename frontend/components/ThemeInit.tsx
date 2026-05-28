"use client";

import { useEffect } from "react";

function applyTheme(theme: "light" | "dark") {
    const root = document.documentElement;

    root.classList.remove("light", "dark");
    root.classList.add(theme);
    root.style.colorScheme = theme;
}

export function ThemeInit() {
    useEffect(() => {
        const saved = localStorage.getItem("theme");
        const prefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;
        const theme = saved === "dark" || (!saved && prefersDark) ? "dark" : "light";

        applyTheme(theme);
    }, []);

    return null;
}