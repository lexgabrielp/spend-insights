"use client";

import Link from "next/link";
import { useEffect, useState } from "react";
import { usePathname, useRouter } from "next/navigation";

const items = [
    { href: "/dashboard", label: "Dashboard" },
    { href: "/upload", label: "Upload" },
    { href: "/transactions", label: "Transactions" },
    { href: "/chat", label: "AI Chat" },
    { href: "/settings", label: "Settings" },
];

function applyTheme(theme: "light" | "dark") {
    const root = document.documentElement;

    root.classList.remove("light", "dark");
    root.classList.add(theme);
    root.style.colorScheme = theme;

    localStorage.setItem("theme", theme);
}

export function Nav() {
    const pathname = usePathname();
    const router = useRouter();
    const [dark, setDark] = useState(false);

    useEffect(() => {
        const saved = localStorage.getItem("theme");
        const prefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;
        const theme = saved === "dark" || (!saved && prefersDark) ? "dark" : "light";

        applyTheme(theme);
        setDark(theme === "dark");
    }, []);

    function toggleDarkMode() {
        const theme = dark ? "light" : "dark";
        applyTheme(theme);
        setDark(theme === "dark");
    }

    function logout() {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        router.push("/login");
    }

    return (
        <aside className="flex min-h-screen w-64 flex-col border-r bg-[var(--card)] p-4 text-[var(--foreground)] transition-colors">
            <div className="mb-8">
                <h1 className="text-2xl font-black text-[var(--foreground)]">
                    Spend Insights
                </h1>

                <p className="text-sm text-slate-500 dark:text-slate-400">
                    Local AI Finance
                </p>
            </div>

            <nav className="flex flex-1 flex-col gap-2">
                {items.map((item) => {
                    const active = pathname === item.href;

                    return (
                        <Link
                            key={item.href}
                            href={item.href}
                            className={`rounded-xl px-4 py-3 text-sm font-medium transition ${
                                active
                                    ? "bg-[var(--primary)] text-[var(--background)]"
                                    : "text-[var(--foreground)] hover:bg-[var(--muted)]"
                            }`}
                        >
                            {item.label}
                        </Link>
                    );
                })}
            </nav>

            <button
                onClick={toggleDarkMode}
                className="mt-6 rounded-xl border border-[var(--border)] px-4 py-3 text-sm font-semibold text-[var(--foreground)] transition hover:bg-[var(--muted)]"
            >
                {dark ? "Light mode" : "Dark mode"}
            </button>

            <button
                onClick={logout}
                className="mt-3 rounded-xl border border-[var(--border)] px-4 py-3 text-sm font-semibold text-[var(--foreground)] transition hover:bg-[var(--muted)]"
            >
                Logout
            </button>
        </aside>
    );
}